package com.sight.users.controllers;

import com.sight.users.entities.UserInRole;
import com.sight.users.services.UserInRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing user roles.
 */
@RestController
public class UserInRoleController {

    private final UserInRoleService userInRoleService;

    @Autowired
    public UserInRoleController(UserInRoleService userInRoleService) {
        this.userInRoleService = userInRoleService;
    }

    /**
     * Assigns a role to a user.
     *
     * @param userId The ID of the user.
     * @param roleId The ID of the role.
     * @return The assigned UserInRole object.
     */
    @Operation(summary = "Assign a role to a user by ID",
            description = "Assigns a specified role to a given user by their IDs.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Role assigned to user",
                            content = @Content(schema = @Schema(implementation = UserInRole.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "User or role not found")
            })
    @PostMapping("/users/{userId}/roles/{roleId}")
    public ResponseEntity<UserInRole> assignRoleToUser(
            @Parameter(description = "ID of the user to whom the role will be assigned") @PathVariable("userId") Integer userId,
            @Parameter(description = "ID of the role to be assigned to the user") @PathVariable("roleId") Integer roleId) {
        UserInRole assignedRole = userInRoleService.assignRoleToUser(userId, roleId);
        return new ResponseEntity<>(assignedRole, HttpStatus.CREATED);
    }

    /**
     * Finds all users by a given role ID with pagination.
     *
     * @param roleId The ID of the role.
     * @param page   The page number for pagination.
     * @param size   The size of the page for pagination.
     * @return A page of UserInRole objects.
     */
    @Operation(summary = "Find users by role",
            description = "Retrieves a paginated list of users assigned to a specified role.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users with the role",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @GetMapping("/users/roles/{roleId}")
    public ResponseEntity<Page<UserInRole>> findUsersByRole(
            @Parameter(description = "ID of the role to find the users for") @PathVariable("roleId") Integer roleId,
            @Parameter(description = "Page number for pagination") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Page size for pagination") @RequestParam(required = false, defaultValue = "1000") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserInRole> usersInRole = userInRoleService.findUsersByRoleName(roleId, pageable);
        return ResponseEntity.ok(usersInRole);
    }
}
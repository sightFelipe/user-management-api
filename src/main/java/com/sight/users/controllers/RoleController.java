package com.sight.users.controllers;

import com.sight.users.entities.Role;
import com.sight.users.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }

    @Operation(summary = "Get all roles", description = "This endpoint returns a list of all roles.")
    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(service.getRoles(), HttpStatus.OK);
    }

    @Operation(summary = "Create a new role", description = "This endpoint creates a new role with the provided data.")
    @PostMapping
    public ResponseEntity<Role> createRole(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Role object to be created",
                    content = @Content(schema = @Schema(implementation = Role.class))
            )
            @RequestBody Role role) {
        return new ResponseEntity<>(service.createRole(role), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing role", description = "This endpoint updates the role with the specified ID with the provided data.")
    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(
            @Parameter(description = "Unique identifier of the role") @PathVariable("roleId") Integer roleId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Role object with updated information",
                    content = @Content(schema = @Schema(implementation = Role.class))
            )
            @RequestBody Role role) {
        return new ResponseEntity<>(service.updateRole(roleId, role), HttpStatus.OK);
    }

    @Operation(summary = "Delete a role", description = "This endpoint deletes the role with the specified ID.")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(
            @Parameter(description = "Unique identifier of the role to be deleted") @PathVariable("roleId") Integer roleId) {
        service.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
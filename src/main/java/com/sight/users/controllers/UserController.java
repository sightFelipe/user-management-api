package com.sight.users.controllers;

import com.sight.users.entities.User;
import com.sight.users.services.UserService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ObservationRegistry observationRegistry;

    /**
     * Creates a new user in the system.
     *
     * @param user The user to be created.
     * @return A ResponseEntity containing the created User object and the HTTP status code.
     */
    @Operation(summary = "Create a new user",
            description = "Creates a new user with the provided user details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid user details provided")
            })
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(summary = "Get a users list", description = "This endpoint returns a paginated list of users.")
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
            @Parameter(description = "Page number for pagination") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Page size for pagination") @RequestParam(required = false, defaultValue = "1000") int size) {
        return Observation.createNotStarted("get.users", observationRegistry)
                .observe(() -> new ResponseEntity<>(service.getUsers(page, size), HttpStatus.OK));
    }

    @Operation(summary = "Get usernames list", description = "This endpoint returns a paginated list of usernames.")
    @GetMapping("/usernames")
    public ResponseEntity<Page<String>> getUsernames(
            @Parameter(description = "Page number for pagination") @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @Parameter(description = "Page size for pagination") @RequestParam(required = false, value = "size", defaultValue = "1000") int size) {
        return new ResponseEntity<>(service.getUsernames(page, size), HttpStatus.OK);
    }

    @Operation(summary = "Get user by ID", description = "This endpoint returns a user based on the provided user ID.")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "Unique identifier of the user") @PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
    }

    @Operation(summary = "Get user by Username", description = "This endpoint returns a user based on the provided username.")
    @GetMapping("/usernames/{username}")
    public ResponseEntity<User> getUserByUsername(
            @Parameter(description = "User username") @PathVariable("username") String username) {
        return new ResponseEntity<>(service.getUserByUsername(username), HttpStatus.OK);
    }

    @Operation(summary = "Authenticate user", description = "This endpoint authenticates a user with the provided credentials.")
    @PostMapping
    public ResponseEntity<User> authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object containing the username and password to authenticate",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
            @RequestBody User user) {
        return new ResponseEntity<>(service.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete a user by username",
            description = "This endpoint deletes a user with the specified username."
    )
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "The username of the user to delete", required = true)
            @PathVariable("username") String username) {

        service.deleteUserbyUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

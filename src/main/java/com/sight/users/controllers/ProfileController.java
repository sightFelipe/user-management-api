package com.sight.users.controllers;

import com.sight.users.entities.Profile;
import com.sight.users.services.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class ProfileController {
    private final ProfileService service;

    @Autowired
    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new profile for a user", description = "This endpoint creates a new profile for the specified user ID.")
    @PostMapping("users/{userId}/profiles")
    public ResponseEntity<Profile> createProfile(
            @Parameter(description = "Unique identifier of the user") @PathVariable("userId") Integer userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile object to be created for the user",
                    content = @Content(schema = @Schema(implementation = Profile.class))
            )
            @RequestBody Profile profile) {
        Profile createdProfile = service.createProfile(userId, profile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a list of profiles", description = "This endpoint returns a paginated list of profiles.")
    @GetMapping("/users/profiles")
    public ResponseEntity<Page<Profile>> getProfiles(
            @Parameter(description = "Page number for pagination") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Page size for pagination") @RequestParam(required = false, defaultValue = "1000") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Profile> profiles = service.getProfiles(pageRequest);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @Operation(summary = "Get profile by User ID and Profile ID", description = "This endpoint returns a profile based on the provided user ID and profile ID.")
    @GetMapping("/users/{userId}/profiles/{profileId}")
    public ResponseEntity<Profile> getById(
            @Parameter(description = "Unique identifier of the user") @PathVariable("userId") Integer userId,
            @Parameter(description = "Unique identifier of the profile") @PathVariable("profileId") Integer profileId) {
        Profile profile = service.getByUserIdAndProfileId(userId, profileId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }


}
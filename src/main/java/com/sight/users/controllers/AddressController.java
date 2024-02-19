package com.sight.users.controllers;
import com.sight.users.entities.Address;
import com.sight.users.services.AddressService;
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
@RequestMapping("/users/{userId}/profiles/{profileId}/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Operation(
            summary = "Find addresses by user and profile ID",
            description = "Retrieve a list of addresses associated with a given user ID and profile ID."
    )
    @GetMapping
    public ResponseEntity<List<Address>> findAddressesByProfileAndUserId(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable("userId") Integer userId,

            @Parameter(description = "ID of the profile", required = true)
            @PathVariable("profileId") Integer profileId) {

        List<Address> addresses = addressService.findAddressesByProfileAndUserId(userId, profileId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @Operation(summary = "Create a new address", description = "This endpoint creates a new address with the provided data for a specific user and profile.")
    @PostMapping
    public ResponseEntity<Address> createAddress(
            @PathVariable Integer userId,
            @PathVariable Integer profileId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Address object to be created",
                    content = @Content(schema = @Schema(implementation = Address.class))
            )
            @RequestBody Address address) {
        Address createdAddress = addressService.createAddress(userId, profileId, address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }


}

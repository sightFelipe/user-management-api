package com.sight.users.services;
import com.sight.users.entities.Address;
import com.sight.users.entities.Profile;
import com.sight.users.repositories.AddressRepository;
import com.sight.users.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<Address> findAddressesByProfileAndUserId(Integer userId, Integer profileId){
        return repository.findByProfileId(userId, profileId);
    }

    public Address createAddress(Integer userId, Integer profileId, Address address) {
        Optional<Profile> result = profileRepository.findByUserIdAndProfileId(userId, profileId);
        if (result.isPresent()) {
            address.setProfile(result.get());
            return repository.save(address);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d and profile %d not found", userId, profileId));
        }
    }

    }


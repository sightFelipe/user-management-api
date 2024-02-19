package com.sight.users.services;

import com.sight.users.entities.Profile;
import com.sight.users.entities.User;
import com.sight.users.repositories.ProfileRepository;
import com.sight.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private UserRepository userRepository;


    public Profile createProfile(Integer userId, Profile profile) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isPresent()) {
            profile.setUser(result.get());
            return repository.save(profile);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId));
        }
    }

    public Page<Profile> getProfiles(Pageable pageable) {
        return repository.findAll(pageable);
    }


    public Profile getByUserIdAndProfileId(Integer userId, Integer profileId) {
        return repository.findByUserIdAndProfileId(userId, profileId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile not found for user %d and profile %d", userId, profileId)));
    }

}
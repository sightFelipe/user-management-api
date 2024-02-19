package com.sight.users.repositories;

import com.sight.users.entities.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("SELECT p FROM Profile p WHERE p.user.id=?1 AND p.id=?2")
    Optional<Profile> findByUserIdAndProfileId(Integer userId, Integer profileId);

    Page<Profile> findAll(Pageable pageable);
}

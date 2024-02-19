package com.sight.users.repositories;


import com.sight.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    public Optional<User> findByUsername(String username);

    public Optional<User> findByUsernameAndPassword(String username, String password);

    /**
     * This isn't SQL, It's JPQL.
     *
     */
    @Query("SELECT u.username FROM User u  ")
    Page<String> findUsernames(Pageable pageable);
}

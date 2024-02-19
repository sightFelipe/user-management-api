package com.sight.users.repositories;

import com.sight.users.entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE a.profile.user.id = ?1 AND a.profile.id = ?2")
    List<Address> findByProfileId(Integer userId, Integer profileId);
}
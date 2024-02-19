package com.sight.users.repositories;
import com.sight.users.entities.UserInRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserInRoleRepository extends JpaRepository<UserInRole, Integer> {
    @Query("SELECT uir FROM UserInRole uir WHERE uir.role.id = :roleId")
    Page<UserInRole> findUsersByRoleName(@Param("roleId") Integer roleName, Pageable pageable);
}

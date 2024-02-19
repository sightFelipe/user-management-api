package com.sight.users.services;

import com.sight.users.entities.Role;

import com.sight.users.repositories.RoleRepository;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> getRoles(){
        return repository.findAll();
    }

    public Role createRole(Role role) {
        return repository.save(role);
    }

    public Role updateRole(Integer roleId, Role role) {
        Optional<Role> result = repository.findById(roleId);
        if (result.isPresent()) {
            Role existingRole = result.get();
            // Update the fields of the existing role with the values from the incoming role
            existingRole.setName(role.getName());
            // ... set other fields as necessary
            return repository.save(existingRole);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d does not exists", roleId));
        }
    }

    public void deleteRole(Integer roleId) {
        Optional<Role> result = repository.findById(roleId);
        if(result.isPresent()){
            repository.delete(result.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d does not exists", roleId));
        }
    }
}

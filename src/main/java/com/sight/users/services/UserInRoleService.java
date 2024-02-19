package com.sight.users.services;

import com.sight.users.entities.Role;
import com.sight.users.entities.User;
import com.sight.users.entities.UserInRole;
import com.sight.users.repositories.RoleRepository;
import com.sight.users.repositories.UserInRoleRepository;
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
public class UserInRoleService {

    private final UserInRoleRepository userInRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserInRoleService(UserInRoleRepository userInRoleRepository,
                             UserRepository userRepository,
                             RoleRepository roleRepository) {
        this.userInRoleRepository = userInRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserInRole assignRoleToUser(Integer userId, Integer roleId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();

            UserInRole userInRole = new UserInRole();
            userInRole.setUsers(user);
            userInRole.setRole(role);

            // Guardar la entidad UserInRole en la base de datos
            return userInRoleRepository.save(userInRole);
        } else {
            // Si uno de los dos no está presente, lanzar una excepción
            if (!userOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User %d not found");
            }
            if (!roleOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role %d not found");
            }
            // Este return es inalcanzable pero necesario para la compilación
            return null;
        }
    }

    public Page<UserInRole> findUsersByRoleName(Integer roleId, Pageable pageable) {
        return userInRoleRepository.findUsersByRoleName(roleId, pageable);
    }
}
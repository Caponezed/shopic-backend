package ru.ystu.shopic_backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ystu.shopic_backend.entity.Role;
import ru.ystu.shopic_backend.entity.User;
import ru.ystu.shopic_backend.exception.ResourceNotFoundException;
import ru.ystu.shopic_backend.repository.RoleRepository;
import ru.ystu.shopic_backend.repository.UserRepository;
import ru.ystu.shopic_backend.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addNewUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + user.getEmail());
        }

        var processedRoles = processUserRoles(user.getRoles());
        user.setRoles(processedRoles);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        var updatableUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id: " + user.getId()));

        updatableUser.setEmail(user.getEmail());
        updatableUser.setFirstName(user.getFirstName());
        updatableUser.setLastName(user.getLastName());

        var processedRoles = processUserRoles(user.getRoles());
        updatableUser.setRoles(processedRoles);

        updatableUser.setPassword(user.getPassword()); // Подумать

        return userRepository.save(updatableUser);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    private List<Role> processUserRoles(List<Role> roles) {
        List<Role> processedRoles = new ArrayList<>();
        for (Role role : roles) {
            Role existingRole = roleRepository.findByName(role.getName())
                    .orElseGet(() -> roleRepository.save(role));
            processedRoles.add(existingRole);
        }
        return processedRoles;
    }
}

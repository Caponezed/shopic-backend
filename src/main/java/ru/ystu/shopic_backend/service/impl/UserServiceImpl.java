package ru.ystu.shopic_backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ystu.shopic_backend.dto.LoginResponseDto;
import ru.ystu.shopic_backend.entity.Role;
import ru.ystu.shopic_backend.entity.User;
import ru.ystu.shopic_backend.exception.ResourceNotFoundException;
import ru.ystu.shopic_backend.repository.RoleRepository;
import ru.ystu.shopic_backend.repository.UserRepository;
import ru.ystu.shopic_backend.service.JWTService;
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
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

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
    public User register(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с email: " + user.getEmail() + " уже существует");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var processedRoles = processUserRoles(user.getRoles());
        user.setRoles(processedRoles);

        return userRepository.save(user);
    }

    @Override
    public LoginResponseDto verify(User user) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        var loginResponseDto = new LoginResponseDto();
        if (!auth.isAuthenticated()) return loginResponseDto;

        var existingUser = userRepository.findUserByEmail(user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с email: " + user.getEmail()));
        var jwtToken = jwtService.generateJWToken(user);

        loginResponseDto.setJwtToken(jwtToken);
        loginResponseDto.setUser(existingUser);

        return loginResponseDto;
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

        var encodedPassword = passwordEncoder.encode(user.getPassword());
        updatableUser.setPassword(encodedPassword);

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

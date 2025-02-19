package ru.ystu.shopic_backend.service;

import ru.ystu.shopic_backend.entity.User;
import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    List<User> getAllUsers();
    User register(User user);

    String verify(User user);

    User updateUser(User user);
    void deleteUserById(Long userId);
}

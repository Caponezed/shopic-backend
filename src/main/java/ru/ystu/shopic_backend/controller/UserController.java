package ru.ystu.shopic_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ystu.shopic_backend.entity.User;
import ru.ystu.shopic_backend.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
        var newUser = userService.register(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public String login(@RequestBody User user) {
        System.out.println(user);
        return "Success";
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        var savedUser = userService.updateUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
    }
}

package ru.ystu.shopic_backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.ystu.shopic_backend.entity.User;

public interface JWTService {
    String generateJWToken(User user);

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);
}

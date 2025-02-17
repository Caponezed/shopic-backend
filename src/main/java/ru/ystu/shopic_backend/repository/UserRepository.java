package ru.ystu.shopic_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ystu.shopic_backend.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
}

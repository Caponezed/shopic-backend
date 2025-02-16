package ru.ystu.shopic_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ystu.shopic_backend.entity.ProductType;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    Optional<ProductType> findByName(String name); // Метод для поиска по имени
}

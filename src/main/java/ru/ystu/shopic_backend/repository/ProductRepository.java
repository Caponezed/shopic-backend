package ru.ystu.shopic_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ystu.shopic_backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

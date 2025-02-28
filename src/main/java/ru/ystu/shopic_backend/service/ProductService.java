package ru.ystu.shopic_backend.service;

import ru.ystu.shopic_backend.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product productDto);
    Product getProductById(Long productId);
    List<Product> getAllProducts();
    Product updateProduct(Product product);
    Product confirmOrder(Product product);
    void deleteProductById(Long productId);
}

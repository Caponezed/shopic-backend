package ru.ystu.shopic_backend.service;

import ru.ystu.shopic_backend.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long productId);
}

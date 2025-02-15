package ru.ystu.shopic_backend.service;

import ru.ystu.shopic_backend.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long productId);
    List<ProductDto> getAllProducts();
    ProductDto updateProductById(ProductDto dtoProduct);
    void deleteProductById(Long productId);
}

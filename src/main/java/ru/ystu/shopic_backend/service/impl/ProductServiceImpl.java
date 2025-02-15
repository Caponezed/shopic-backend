package ru.ystu.shopic_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ystu.shopic_backend.dto.ProductDto;
import ru.ystu.shopic_backend.entity.Product;
import ru.ystu.shopic_backend.exception.ResourceNotFoundException;
import ru.ystu.shopic_backend.mapper.ProductMapper;
import ru.ystu.shopic_backend.repository.ProductRepository;
import ru.ystu.shopic_backend.service.ProductService;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.mapToProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Товар не найден с id: " + productId));

        return ProductMapper.mapToProductDto(product);
    }
}

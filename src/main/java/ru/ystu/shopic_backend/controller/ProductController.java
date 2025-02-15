package ru.ystu.shopic_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ystu.shopic_backend.dto.ProductDto;
import ru.ystu.shopic_backend.service.ProductService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;

    // Build add product rest api
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.createProduct(productDto);
        return new ResponseEntity<ProductDto>(savedProduct, HttpStatus.CREATED);
    }
}

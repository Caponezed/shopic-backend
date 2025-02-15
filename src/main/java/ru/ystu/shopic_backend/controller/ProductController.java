package ru.ystu.shopic_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }
}

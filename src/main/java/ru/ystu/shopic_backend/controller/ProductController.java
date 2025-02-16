package ru.ystu.shopic_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ystu.shopic_backend.entity.Product;
import ru.ystu.shopic_backend.service.ProductService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product productDto) {
        Product savedProduct = productService.createProduct(productDto);
        return new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long productId) {
        Product productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> dtoProducts = productService.getAllProducts();
        return ResponseEntity.ok(dtoProducts);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product updatedProduct) {
       Product product = productService.updateProduct(updatedProduct);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok("Товар с id: " + productId + " был успешно удалён");
    }
}

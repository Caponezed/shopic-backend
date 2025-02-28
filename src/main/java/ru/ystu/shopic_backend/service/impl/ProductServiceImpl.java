package ru.ystu.shopic_backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ystu.shopic_backend.entity.Product;
import ru.ystu.shopic_backend.entity.ProductType;
import ru.ystu.shopic_backend.exception.ResourceNotFoundException;
import ru.ystu.shopic_backend.repository.ProductRepository;
import ru.ystu.shopic_backend.repository.ProductTypeRepository;
import ru.ystu.shopic_backend.service.ProductService;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    @Transactional
    public Product createProduct(Product newProduct) {
        ProductType productType = newProduct.getProductType();

        if (productType != null && productType.getId() == null) {
            ProductType existingType = productTypeRepository.findByName(productType.getName())
                    .orElse(null);

            if (existingType != null) {
                newProduct.setProductType(existingType);
            } else {
                productTypeRepository.save(productType);
            }
        }

        // Сохраняем продукт
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Товар не найден с id: " + productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product updatedProduct) {
        Long productId = updatedProduct.getId();
        Product updatableProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Товар не найден с id: " + productId));

        updatableProduct.setName(updatedProduct.getName());
        updatableProduct.setDescription(updatedProduct.getDescription());
        updatableProduct.setProductType(updatedProduct.getProductType());
        updatableProduct.setPrice(updatedProduct.getPrice());
        updatableProduct.setTotalQuantity(updatedProduct.getTotalQuantity());
        updatableProduct.setImgSrc(updatedProduct.getImgSrc());

        return productRepository.save(updatableProduct);
    }

    public Product confirmOrder(Product confirmedProduct) {
        Long productId = confirmedProduct.getId();
        Product updatableProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Товар не найден с id: " + productId));

        updatableProduct.setTotalQuantity(confirmedProduct.getTotalQuantity());

        return productRepository.save(updatableProduct);
    }

    @Override
    public void deleteProductById(Long productId) {
        productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Товар не найден с id: " + productId));
        productRepository.deleteById(productId);
    }
}

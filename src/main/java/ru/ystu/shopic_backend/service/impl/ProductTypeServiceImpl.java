package ru.ystu.shopic_backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ystu.shopic_backend.entity.ProductType;
import ru.ystu.shopic_backend.exception.ResourceNotFoundException;
import ru.ystu.shopic_backend.repository.ProductTypeRepository;
import ru.ystu.shopic_backend.service.ProductTypeService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    public Optional<ProductType> getProductTypeById(Long id) {
        return productTypeRepository.findById(id);
    }

    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @Override
    public ProductType saveProductType(ProductType newProductType) {
        return productTypeRepository.save(newProductType);
    }

    @Override
    public void deleteProductTypeById(Long id) {
        productTypeRepository.deleteById(id);
    }

    @Override
    public ProductType updateProductTypeById(ProductType updatedProductType) {
        ProductType updatableProductType = productTypeRepository.findById(updatedProductType.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Тип товара не найден с id: " + updatedProductType.getId()));

        updatableProductType.setName(updatableProductType.getName());

        return productTypeRepository.save(updatableProductType);
    }
}

package ru.ystu.shopic_backend.service;

import ru.ystu.shopic_backend.entity.ProductType;

import java.util.List;
import java.util.Optional;

public interface ProductTypeService {
   Optional<ProductType> getProductTypeById(Long id);
   List<ProductType> getAllProductTypes();
   ProductType saveProductType(ProductType newProductType);
   void deleteProductTypeById(Long id);
   ProductType updateProductTypeById(ProductType updatedProductType);
}

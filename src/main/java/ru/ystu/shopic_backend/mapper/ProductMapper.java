package ru.ystu.shopic_backend.mapper;

import ru.ystu.shopic_backend.dto.ProductDto;
import ru.ystu.shopic_backend.entity.Product;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getType(),
                product.getPrice(),
                product.getImgSrc()
        );
    }

    public static Product mapToProduct(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getType(),
                productDto.getPrice(),
                productDto.getImgSrc()
        );
    }
}

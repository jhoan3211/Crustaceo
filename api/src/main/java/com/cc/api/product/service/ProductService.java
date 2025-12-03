package com.cc.api.product.service;

import com.cc.api.product.dto.request.ProductRequest;
import com.cc.api.product.dto.response.ProductResponse;
import com.cc.api.product.entity.ProductEntity;
import com.cc.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse create(ProductRequest product) {

        ProductEntity saveProduct = save(product);

        return ProductResponse.builder()
                .name(saveProduct.getName())
                .description(saveProduct.getDescription())
                .build();
    }

    private ProductEntity save(ProductRequest product){

        ProductEntity newProduct = ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();

        return productRepository.save(newProduct);
    }


}
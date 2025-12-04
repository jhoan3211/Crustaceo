package com.cc.api.product.service;

import com.cc.api.product.dto.request.ProductRequest;
import com.cc.api.product.dto.response.ProductResponse;
import com.cc.api.product.entity.ProductEntity;
import com.cc.api.product.mapper.ProductMapper;
import com.cc.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse create(ProductRequest productRequest) {

        ProductEntity entity = productMapper.toEntity(productRequest);

        ProductEntity saved = productRepository.save(entity);

        return productMapper.toResponse(saved);
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }









}
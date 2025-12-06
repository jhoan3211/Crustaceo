package com.cc.api.product.service;

import com.cc.api.product.dto.request.ProductRequest;
import com.cc.api.product.dto.response.ProductResponse;
import com.cc.api.product.entity.ProductEntity;
import com.cc.api.product.mapper.ProductMapper;
import com.cc.api.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public List<ProductResponse> create(List<ProductRequest> productRequests) {

        List<ProductEntity> entities = productRequests.stream()
                .map(productMapper::toEntity)
                .toList();

        List<ProductEntity> savedEntities = productRepository.saveAll(entities);

        return productMapper.toResponse(savedEntities);
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductResponse> getAllProducts() {
        List<ProductEntity> entities = productRepository.findAll();
        return productMapper.toResponse(entities);
    }

    public ProductEntity findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("Product not found with id: " + productId)
        );
    }
}
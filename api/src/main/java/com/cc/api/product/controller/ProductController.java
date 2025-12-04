package com.cc.api.product.controller;

import com.cc.api.product.dto.request.ProductRequest;
import com.cc.api.product.dto.response.ProductResponse;
import com.cc.api.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest user) {
        ProductResponse response = productService.create(user);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping()
    public ResponseEntity<Void> delete(@Valid @PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    


}

package com.cc.api.product.controller;

import com.cc.api.auth.dto.response.LoginResponseDTO;
import com.cc.api.product.dto.request.ProductRequest;
import com.cc.api.product.dto.response.ProductResponse;
import com.cc.api.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest user) {
        ProductResponse response = productService.create(user);
        return ResponseEntity.ok(response);
    }



}

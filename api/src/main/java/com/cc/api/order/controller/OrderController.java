package com.cc.api.order.controller;

import com.cc.api.auth.entity.UserEntity;
import com.cc.api.order.dto.request.OrderRequest;
import com.cc.api.order.dto.response.OrderResponse;
import com.cc.api.order.service.OrderService;
import com.cc.api.product.dto.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping()
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest order) {
        String email = getMyEmail();
        OrderResponse response = orderService.create(order, email);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders()  {
        String email = getMyEmail();
        List<OrderResponse> orders = orderService.getOrdersByEmail(email);

        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getOrders()  {
        List<OrderResponse> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }



    private String getMyEmail() {

        var auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return auth.getName();

    }




}

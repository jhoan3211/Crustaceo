package com.cc.api.order.controller;

import com.cc.api.order.dto.request.OrderRequest;
import com.cc.api.order.dto.response.OrderResponse;
import com.cc.api.order.enums.OrderStatus;
import com.cc.api.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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


    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping()
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest order) {
        String email = getMyEmail();
        OrderResponse response = orderService.create(order, email);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders()  {
        String email = getMyEmail();
        List<OrderResponse> orders = orderService.getOrdersByEmail(email);

        return ResponseEntity.ok(orders);
    }



    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/active")
    public ResponseEntity<List<OrderResponse>> getMyActiveOrders()  {
        String email = getMyEmail();
        List<OrderResponse> orders = orderService.getActiveOrdersByEmail(email);

        return ResponseEntity.ok(orders);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getOrders()  {
        List<OrderResponse> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }


    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId)  {
        OrderResponse order = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(order);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}")
        public ResponseEntity<OrderResponse> changeStateOrder(@PathVariable Long orderId, @RequestParam OrderStatus status )  {
        OrderResponse order = orderService.changeStateOrder(orderId,status);
        return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId)  {
        OrderResponse order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/active")
    public ResponseEntity<List<OrderResponse>> getActiveOrders()  {
        List<OrderResponse> orders = orderService.getActiveOrders();

        return ResponseEntity.ok(orders);
    }



    private String getMyEmail() {

        var auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return auth.getName();

    }




}

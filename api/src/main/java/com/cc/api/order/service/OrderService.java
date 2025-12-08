package com.cc.api.order.service;

import com.cc.api.auth.entity.UserEntity;
import com.cc.api.auth.service.UserService;
import com.cc.api.order.enums.OrderStatus;
import com.cc.api.order.dto.request.OrderDetailRequest;
import com.cc.api.order.dto.request.OrderRequest;
import com.cc.api.order.dto.response.OrderResponse;
import com.cc.api.order.entity.OrderDetailEntity;
import com.cc.api.order.entity.OrderEntity;
import com.cc.api.order.mapper.OrderMapper;
import com.cc.api.order.repository.OrderRepository;
import com.cc.api.product.entity.ProductEntity;
import com.cc.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;
    private final UserService userService;
    private final OrderMapper orderMapper;



    public List<OrderResponse> getOrdersByEmail(String email) {
        Long userId = userService.getUserByEmail(email).getId();
        List<OrderEntity> orders = orderRepository.findByUserId(userId);

        return orderMapper.toResponse(orders);
    }

    public List<OrderResponse> getActiveOrdersByEmail(String email) {
        Long userId = userService.getUserByEmail(email).getId();
        List<OrderEntity> orders =
                orderRepository.findByUserIdAndStatusIn(
                userId,
                List.of(
                        OrderStatus.PENDING,
                        OrderStatus.PREPARATION,
                        OrderStatus.OUT_FOR_DELIVERY
                )
        );

        return orderMapper.toResponse(orders);
    }

    public List<OrderResponse> getAllOrders(){
        List<OrderEntity> orders = orderRepository.findAll();

        return orderMapper.toResponse(orders);
    }

    @Transactional
    public OrderResponse create(OrderRequest orderRequest, String email) {

        UserEntity user = userService.getUserByEmail(email);
        OrderEntity order = buildBaseOrder(orderRequest, user);
        List<OrderDetailEntity> details = buildOrderDetails(orderRequest, order);
        BigDecimal total = calculateTotal(details);

        order.setOrderDetails(details);
        order.setAmount(total);

        OrderEntity saved = orderRepository.save(order);
        return orderMapper.toResponse(saved);
    }

    private OrderEntity buildBaseOrder(OrderRequest orderRequest, UserEntity user) {
        OrderEntity order = new OrderEntity();
        order.setAddress(orderRequest.getAddress());
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setAmount(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    private List<OrderDetailEntity> buildOrderDetails(OrderRequest orderRequest, OrderEntity order) {
        List<OrderDetailEntity> details = new ArrayList<>();

        for (OrderDetailRequest detailReq : orderRequest.getOrderDetails()) {

            ProductEntity product = productService.findById(detailReq.getProductId());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(detailReq.getQuantity()));

            OrderDetailEntity detail = OrderDetailEntity.builder()
                    .id("O"+order.getId()+"P"+product.getId())
                    .order(order)
                    .product(product)
                    .quantity(detailReq.getQuantity())
                    .subtotal(subtotal)
                    .build();

            details.add(detail);
        }

        return details;
    }

    private BigDecimal calculateTotal(List<OrderDetailEntity> details) {
        return details.stream()
                .map(OrderDetailEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}

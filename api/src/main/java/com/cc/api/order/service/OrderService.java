package com.cc.api.order.service;

import com.cc.api.auth.service.UserService;
import com.cc.api.order.dto.request.OrderRequest;
import com.cc.api.order.dto.response.OrderResponse;
import com.cc.api.order.entity.OrderEntity;
import com.cc.api.order.mapper.OrderMapper;
import com.cc.api.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;



    public List<OrderResponse> getOrdersByEmail(String email) {
        Long userId = userService.getIdByEmail(email);
        List<OrderEntity> orders = orderRepository.findByUserId(userId);

        return orderMapper.toResponse(orders);
    }

    public List<OrderResponse> getAllOrders(){
        List<OrderEntity> orders = orderRepository.findAll();

        return orderMapper.toResponse(orders);
    }

    @Transactional
    public OrderResponse create(OrderRequest order) {
        //TODO
        


        return ;
    }


}

package com.cc.api.order.repository;

import com.cc.api.order.enums.OrderStatus;
import com.cc.api.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserId(Long userId);


    List<OrderEntity> findByUserIdAndStatusIn(Long userId, List<OrderStatus> statuses);
}

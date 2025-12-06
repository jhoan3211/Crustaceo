package com.cc.api.order.dto.response;

import com.cc.api.auth.dto.response.UserOrderResponse;
import com.cc.api.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String address;

    private BigDecimal amount;

    private OrderStatus status;

    private UserOrderResponse user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<OrderDetailResponse> orderDetails;

}

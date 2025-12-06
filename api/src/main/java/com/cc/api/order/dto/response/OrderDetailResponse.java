package com.cc.api.order.dto.response;

import com.cc.api.product.dto.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {

    private String id;

    private ProductResponse product;

    private Integer quantity;

    private BigDecimal subtotal;

}

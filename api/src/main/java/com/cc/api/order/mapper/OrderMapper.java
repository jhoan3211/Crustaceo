package com.cc.api.order.mapper;

import com.cc.api.order.dto.request.OrderRequest;
import com.cc.api.order.dto.response.OrderResponse;
import com.cc.api.order.entity.OrderEntity;
import com.cc.api.product.dto.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderResponse toResponse(OrderEntity request);


    List<OrderResponse> toResponse(List<OrderEntity> request);
}

package com.cc.api.product.mapper;

import com.cc.api.product.dto.request.ProductRequest;
import com.cc.api.product.dto.response.ProductResponse;
import com.cc.api.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductEntity toEntity(ProductRequest request);

    ProductResponse toResponse(ProductEntity entity);
}

package com.cc.api.order.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    @Valid
    @NotEmpty(message = "Debe contener al menos un producto")
    private List<OrderDetailRequest> orderDetails;

}

package com.cc.api.auth.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    private static final String BLOCK_PLANKTON_REGEX =
            "^(?!.*(?i)p+[^a-zA-Z0-9]*[l1!]+[^a-zA-Z0-9]*[a4áàä]+[^a-zA-Z0-9]*[nñ]+[^a-zA-Z0-9]*[ckq]+[^a-zA-Z0-9]*t+[^a-zA-Z0-9]*[o0óòö]+[^a-zA-Z0-9]*n+).*$";

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(
            regexp = BLOCK_PLANKTON_REGEX,
            message = "La fórmula no te pertenece"
    )
    private String name;

    @Email(message = "El correo no tiene formato de Email")
    @Pattern(
            regexp = BLOCK_PLANKTON_REGEX,
            message = "La fórmula no te pertenece"
    )
    private String email;

    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "La contraseña debe tener mayúscula, minúscula y número"
    )
    private String password;
}
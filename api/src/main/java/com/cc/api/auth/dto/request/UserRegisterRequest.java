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

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(
            regexp = "^(?!.*(?i)plankton).*$",
            message = "La formula no te pertenece"
    )
    private String name;

    @Email(message = "el correo no tiene formato de Email")
    @Pattern(
            regexp = "^(?!.*(?i)plankton).*$",
            message = "La formula no te pertenece"
    )
    private String email;

    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "La contraseña debe tener mayúscula, minúscula y número"
    )
    private String password;
}

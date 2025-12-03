package com.cc.api.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @Email(message = "el correo no tiene formato de Email")
    @Pattern(
            regexp = "^(?!.*(?i)plankton).*$",
            message = "La formula no te pertenece"
    )
    private String email;

    @NotBlank
    private String password;
}

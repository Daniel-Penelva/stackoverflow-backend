package com.api.stackoverflow_backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDTO {
    
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 3)
    private String password;
}

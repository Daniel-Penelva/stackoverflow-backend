package com.api.stackoverflow_backend.dtos;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String name;
    private String email;
    private String password;
    
}

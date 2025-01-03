package com.api.stackoverflow_backend.entities;

import com.api.stackoverflow_backend.dtos.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String name;

    @NotNull(message = "O nome é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "A senha é obrigatória")
    @Size(min = 3, message = "A senha deve ter pelo menos 3 caracteres")
    private String password;

    public UserDTO getUserDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setName(name);
        userDTO.setEmail(email);
        return userDTO;
    }

}

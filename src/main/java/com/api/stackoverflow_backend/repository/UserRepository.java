package com.api.stackoverflow_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.stackoverflow_backend.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método de consulta para encontrar um usuário por e-mail
    Optional<User> findByEmail(String email);

}

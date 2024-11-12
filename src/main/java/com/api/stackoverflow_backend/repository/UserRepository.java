package com.api.stackoverflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.stackoverflow_backend.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
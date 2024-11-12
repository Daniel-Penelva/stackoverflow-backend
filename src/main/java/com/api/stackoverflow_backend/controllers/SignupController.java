package com.api.stackoverflow_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.stackoverflow_backend.dtos.SignupDTO;
import com.api.stackoverflow_backend.dtos.UserDTO;
import com.api.stackoverflow_backend.services.user.UserService;

import jakarta.validation.Valid;

@RestController
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupDTO signupDTO) {
        UserDTO createdUser = userService.createUser(signupDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}

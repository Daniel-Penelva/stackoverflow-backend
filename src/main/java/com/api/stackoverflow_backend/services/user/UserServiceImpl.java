package com.api.stackoverflow_backend.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.SignupDTO;
import com.api.stackoverflow_backend.dtos.UserDTO;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {

        // Verifica se o e-mail já existe
        if (userRepository.findByEmail(signupDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setName(signupDTO.getName());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        User createdUser = userRepository.save(user);

        UserDTO createdUserDTO = new UserDTO();
        createdUserDTO.setId(createdUser.getId());
        createdUserDTO.setName(createdUser.getName());
        createdUserDTO.setEmail(createdUser.getEmail());

        return createdUserDTO;
    }

}

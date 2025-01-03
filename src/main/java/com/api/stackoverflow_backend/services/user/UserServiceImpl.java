package com.api.stackoverflow_backend.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.dtos.SignupDTO;
import com.api.stackoverflow_backend.dtos.UserDTO;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.exceptions.EmailAlreadyExistsException;
import com.api.stackoverflow_backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {

        if (userRepository.findByEmail(signupDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("O e-mail " + signupDTO.getEmail() + " já está em uso.");
        }


        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setName(signupDTO.getName());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        User createdUser = userRepository.save(user);

        return mapToUserDTO(createdUser);
    }

    // Mapper - método para converter em DTO
    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

}

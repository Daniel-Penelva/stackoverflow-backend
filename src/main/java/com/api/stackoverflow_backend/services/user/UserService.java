package com.api.stackoverflow_backend.services.user;

import com.api.stackoverflow_backend.dtos.SignupDTO;
import com.api.stackoverflow_backend.dtos.UserDTO;

public interface UserService {
    
    UserDTO createUser(SignupDTO signupDTO);
}

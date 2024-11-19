package com.api.stackoverflow_backend.services.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Escrevendo a lógica para obter o usuário do banco de dados
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não existe!");
        }
        return new org.springframework.security.core.userdetails.User(userOptional.get().getEmail(),
                userOptional.get().getPassword(), new ArrayList<>());
    }

}

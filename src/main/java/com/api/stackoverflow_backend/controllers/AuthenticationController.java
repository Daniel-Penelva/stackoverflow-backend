package com.api.stackoverflow_backend.controllers;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.stackoverflow_backend.dtos.AuthenticationRequest;
import com.api.stackoverflow_backend.entities.User;
import com.api.stackoverflow_backend.repository.UserRepository;
import com.api.stackoverflow_backend.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    // http://localhost:8080/authentication
    @PostMapping("/authentication")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("E-mail ou senha incorretos!");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .toString());
        }

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers",
                "Authorization," + " X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);

    }
    
}


/** OBSERVAÇÃO.
 * 
 * Fluxo do método createAuthenticationToken:
 *  1. Autenticação:
 *      -> O authenticationManager valida o email e a senha enviados.
 * 
 *  2. Carregamento do usuário:
 *      -> O serviço de detalhes do usuário (UserDetailsService) carrega as informações do usuário.
 * 
 *  3. Busca no banco:
 *      -> O repositório busca o usuário completo para obter o ID.
 * 
 *  4. Geração de token: 
 *      -> Um token JWT é criado com o username.
 * 
 *  5. Resposta ao Cliente:
 *      -> O ID do usuário é enviado no corpo da resposta como JSON.
 *      -> O token JWT é incluído nos cabeçalhos.
*/

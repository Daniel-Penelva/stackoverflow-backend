package com.api.stackoverflow_backend.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.stackoverflow_backend.services.jwt.UserDetailsServiceImpl;
import com.api.stackoverflow_backend.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    // Construtor
    public JwtRequestFilter(UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");  // Recupera o cabeçalho Authorization que contém o token JWT no formato Bearer <token>
        String token = null;
        String username = null;

        // verificar se o Token JWT Existe e está no Formato Correto
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // Extrai o token removendo o prefixo "Bearer "
            username = jwtUtil.extractUsername(token);   // Obtém o nome de usuário do token
                
        }

        // Verifica se o username foi extraído e se o SecurityContext ainda não possui uma autenticação configurada.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);  // carrega os detalhes do usuário através do UserDetailsService

            // Valida o token
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,  userDetails.getAuthorities()); // cria o objeto com as credenciais do usuário
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);  // add a autenticação ao SecurityContextHolder
            }
        }

        filterChain.doFilter(request, response);  // Encaminha a requisição para próximo filtro na cadeia.

    }
    
}

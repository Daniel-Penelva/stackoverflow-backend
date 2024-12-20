package com.api.stackoverflow_backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.stackoverflow_backend.filter.JwtRequestFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    // Construtor
    public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/authentication", "/sign-up").permitAll() // Permite acesso público ao endpoint de registro
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/**").authenticated()            // Protege todas as outras rotas
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)   // Insere o jwtRequestFilter antes do filtro de autenticação. Garante que o token JWT seja validado antes de qualquer outro filtro processar a requisição. 
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}

/**
 * jwtRequestFilter:
 *  -> Intercepta cada requisição.
 *  -> Verifica o cabeçalho de autorização para um token JWT.
 *  -> Valida o token JWT e configura o contexto de segurança (SecurityContextHolder).
 * 
 * addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) insere o jwtRequestFilter antes de UsernamePasswordAuthenticationFilter.
 * O que garante que todas as requisições passem primeiro pelo filtro JWT, mesmo que a autenticação padrão do Spring Security (username/password) 
 * esteja habilitada.
 * 
 * SessionCreationPolicy.STATELESS configura a política de sessão como stateless, ou seja, nenhuma sessão é criada ou armazenada no servidor.
 * Essencial em aplicações que usam JWT, pois a autenticação é gerenciada pelo token e não por sessões.
*/

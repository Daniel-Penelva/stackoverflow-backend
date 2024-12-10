package com.api.stackoverflow_backend.utils;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.security.Key;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

@Component
public class JwtUtil {

    // chave secreta para assinar o token JWT
    public static final String SECRET = "ByPq7X8ih9X0DGNMMAf425mKLJSYC7HKVxfb7pMT4osXmMwbBREdxK8QGE4rW8qb7A";

    // Método para gerar o token
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))  // expira em 30 minutos
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    // Método para recuperar a chave usada para assinar o JWT.
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Método para gerar um token JWT com um nome de usuário.
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    // Método para extrair todas as "claims" de um token JWT.
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para extrair uma claim específica do token JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Método para extrair a data de expiração do token.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Método para extrair o nome de usuário (subject) do token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método para verificar se o token já expirou.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Este método valida se o token é válido e se pertence ao usuário correto
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
}

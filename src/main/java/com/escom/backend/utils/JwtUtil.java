package com.escom.backend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.escom.backend.domain.entities.Rol;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secretBase64) {
        byte[] secretBytes = Base64.getDecoder().decode(secretBase64);
        this.key = Keys.hmacShaKeyFor(secretBytes);
    }

    public String generateToken(String email, Rol rol, UUID userId) {
        return Jwts.builder()
            .setSubject(email)
            .claim("rol", rol.name())
            .claim("userId", userId.toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
            .signWith(key)
            .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}

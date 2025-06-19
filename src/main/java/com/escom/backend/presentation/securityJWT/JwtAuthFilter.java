package com.escom.backend.presentation.securityJWT;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.escom.backend.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response, 
    FilterChain filterChain) throws ServletException, IOException {
      String authHeader = request.getHeader("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          filterChain.doFilter(request, response);
          return;
      }

      String token = authHeader.substring(7);
      try {
        Claims claims = jwtUtil.validateToken(token);
        String email = claims.getSubject();
        String rol = claims.get("rol", String.class);
        String id = claims.get("id", String.class);

        Map<String, Object> details = new HashMap<>();
            details.put("id", id);
            details.put("email", email);
        
            var auth = new UsernamePasswordAuthenticationToken(
            email,
            null,
            Collections.singletonList(new SimpleGrantedAuthority(rol))
        );

        auth.setDetails(details);

        /* Almacena la autenticación en el contexto de seguridad,
        permite saber qué usuario está realizando la petición.
        Aplicar reestricciones */ 
        SecurityContextHolder.getContext().setAuthentication(auth); 
        filterChain.doFilter(request, response);
      } catch (Exception e) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
          return;
      }
    }
}
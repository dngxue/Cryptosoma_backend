package com.escom.backend.presentation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.LoginRequest;
import com.escom.backend.domain.dto.LoginResponse;
import com.escom.backend.domain.entities.users.Usuario;
import com.escom.backend.domain.repositories.UsuarioRepository;
import com.escom.backend.utils.JwtUtil;

@Service
public class AuthService {

  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private UsuarioRepository usuarioRepository;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  
  public LoginResponse login(LoginRequest request) {
    Usuario user = usuarioRepository.findByEmail(request.email)
      .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    if(!encoder.matches(request.password, user.getPassword())) {
      throw new RuntimeException("La contraseña no coincide");
    }

    String token = jwtUtil.generateToken(user.getEmail(), user.getRol(), user.getId());

    return new LoginResponse(user.getId(), user.getNombre(), user.getEmail(), user.getRol(), token);
  }
}

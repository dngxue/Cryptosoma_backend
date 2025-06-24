package com.escom.backend.presentation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.login.LoginRequest;
import com.escom.backend.domain.dto.login.LoginResponse;
import com.escom.backend.domain.dto.login.LoginResponseFarmaceutico;
import com.escom.backend.domain.dto.login.LoginResponseMedico;
import com.escom.backend.domain.dto.login.LoginResponsePaciente;
import com.escom.backend.domain.entities.users.Farmaceutico;
import com.escom.backend.domain.entities.users.Medico;
import com.escom.backend.domain.entities.users.Paciente;
import com.escom.backend.domain.entities.users.Rol;
import com.escom.backend.domain.entities.users.Usuario;
import com.escom.backend.domain.repositories.FarmaceuticoRepository;
import com.escom.backend.domain.repositories.MedicoRepository;
import com.escom.backend.domain.repositories.PacienteRepository;
import com.escom.backend.domain.repositories.UsuarioRepository;
import com.escom.backend.utils.JwtUtil;

@Service
public class AuthService {

  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private PacienteRepository pacienteRepository;
  @Autowired 
  private FarmaceuticoRepository farmaceuticoRepository;
  @Autowired
  private MedicoRepository medicoRepository;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  
  public LoginResponse login(LoginRequest request) {
    Usuario user = usuarioRepository.findByEmail(request.email)
      .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    if(!encoder.matches(request.password, user.getPassword())) {
      throw new RuntimeException("La contraseña no coincide");
    }
    String token = jwtUtil.generateToken(user.getEmail(), user.getRol(), user.getId());


    if(user.getRol() == Rol.PACIENTE) {
      Paciente paciente = pacienteRepository.findById(user.getId())
        .orElseThrow(() -> new RuntimeException("Paciente no encontrado en la base de datos"));
    
      return new LoginResponsePaciente(
        user, 
        token, 
        paciente.getCurp(), 
        paciente.getMatricula());
    }

    if(user.getRol() == Rol.MEDICO) {
      Medico medico = medicoRepository.findById(user.getId())
        .orElseThrow(() -> new RuntimeException("Médico no encontrado en la base de datos"));

      return new LoginResponseMedico(
        user, token, 
        medico.getCedula(), 
        medico.getEspecialidad(), 
        medico.getClinica(), 
        medico.getTelefono());
    }

    if(user.getRol() == Rol.FARMACEUTICO) {
      Farmaceutico farmaceutico = farmaceuticoRepository.findById(user.getId())
        .orElseThrow(() -> new RuntimeException("Farmacéutico no encontrado en la base de datos"));
      
      return new LoginResponseFarmaceutico(
        user, 
        token, 
        farmaceutico.getTelefono(), 
        farmaceutico.getFarmacia());
    }
    
    throw new RuntimeException("Rol no reconocido: " + user.getRol());
  }
}

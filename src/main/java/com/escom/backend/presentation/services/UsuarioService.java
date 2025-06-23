package com.escom.backend.presentation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.users.get.GetPatientResponseDTO;
import com.escom.backend.domain.entities.users.Paciente;
import com.escom.backend.domain.entities.users.Usuario;
import com.escom.backend.domain.repositories.PacienteRepository;

@Service
public class UsuarioService {

  @Autowired private PacienteRepository pacienteRepository;

  public GetPatientResponseDTO getUsuarioByMatricula(String matricula) {
    Paciente paciente = pacienteRepository.findByMatricula(matricula)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + matricula));
    
    Usuario usuario = paciente.getUsuario();
    return new GetPatientResponseDTO(paciente.getId(), paciente.getMatricula(), paciente.getCurp(), usuario.getNombre(), usuario.getFechaNacimiento());
  }
}

package com.escom.backend.presentation.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.security.CreatePrivateKeyUserDTO;
import com.escom.backend.domain.dto.shared.ResponseDTO;
import com.escom.backend.domain.entities.security.PrivateKeyUser;
import com.escom.backend.domain.entities.users.Usuario;
import com.escom.backend.domain.repositories.PrivateKeyUserRepository;
import com.escom.backend.domain.repositories.UsuarioRepository;

@Service
public class PrivateKeyUserService {
  @Autowired private PrivateKeyUserRepository privateKeyUserRepository;
  @Autowired private UsuarioRepository usuarioRepository;

  public ResponseDTO createPrivateKeyUser(CreatePrivateKeyUserDTO createPrivateKeyUserDTO) {
    Usuario usuario = usuarioRepository.findById(createPrivateKeyUserDTO.idUser())
      .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + createPrivateKeyUserDTO.idUser()));
    
    
    PrivateKeyUser privateKey = new PrivateKeyUser();
    privateKey.setUsuario(usuario);
    privateKey.setEncryptedKey(createPrivateKeyUserDTO.encryptedKey());
    privateKeyUserRepository.save(privateKey);
    return new ResponseDTO("success", "Llave privada almacenada con éxito");
  }
}

package com.escom.backend.presentation.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escom.backend.domain.dto.security.PublicKeyDTO;
import com.escom.backend.domain.entities.Usuario;
import com.escom.backend.domain.entities.security.PublicKeyUser;
import com.escom.backend.domain.repositories.PublicKeyRepository;
import com.escom.backend.domain.repositories.UsuarioRepository;

@Service
public class PublicKeyService {
  @Autowired
  private PublicKeyRepository publicKeyRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public void savePublicKey(PublicKeyDTO publicKeyDto) {

    Usuario usuario = usuarioRepository.findById(publicKeyDto.usuario_id)
      .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));


    PublicKeyUser publicKeyUser = new PublicKeyUser();

    publicKeyUser.setPublicKey(publicKeyDto.publicKey);
    publicKeyUser.setUsuario(usuario);
    publicKeyUser.setKeyType(publicKeyDto.keyType);

    publicKeyRepository.save(publicKeyUser);
  }
}

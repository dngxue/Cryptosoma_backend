package com.escom.backend.domain.entities.security;

import java.time.LocalDateTime;
import java.util.UUID;

import com.escom.backend.domain.entities.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Define la entidad que representa la clave pública de un usuario.
 * Esta entidad se utiliza para almacenar la clave pública
 * asociada a un usuario específico en el sistema.
 */

@Entity
@Table(name = "public_key_user")
public class PublicKeyUser {
  @Id
  @GeneratedValue
  public UUID id_publicKey;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "tipo")
  private KeyType keyType;

  @Column(nullable = false, name = "public_key")
  private String publicKey;

  private LocalDateTime createdAt = LocalDateTime.now();

  // == Setters ==
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
  public void setKeyType(KeyType keyType) {
    this.keyType = keyType;
  }
  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }
  // == Getters ==
  public UUID getIdPublicKey() {
    return id_publicKey;
  }
  public Usuario getUsuario() {
    return usuario;
  }
  public KeyType getKeyType() {
    return keyType;
  }
  public String getPublicKey() {
    return publicKey;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}

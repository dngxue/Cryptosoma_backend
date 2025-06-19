package com.escom.backend.domain.entities.security;

import java.util.UUID;

import com.escom.backend.domain.entities.Prescription;
import com.escom.backend.domain.entities.users.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "access_key")
public class AccessKey {
  @Id
  @GeneratedValue
  public UUID id;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "prescription_id", nullable = false)
  private Prescription prescription;

  @Column(nullable = false)
  private String key;

  @Column(name="server_public_key", nullable = false)
  private String serverPublicKey;

  // == Setters ==
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public void setPrescription(Prescription prescription) {
    this.prescription = prescription;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setServerPublicKey(String serverPublicKey) {
    this.serverPublicKey = serverPublicKey;
  }

  // == Getters ==
  public Usuario getUsuario() {
    return this.usuario;
  }
  public Prescription getPrescription() {
    return this.prescription;
  }
  public String getKey() {
    return this.key;
  }
  public String getServerPublicKey() {
    return this.serverPublicKey;
  }
}

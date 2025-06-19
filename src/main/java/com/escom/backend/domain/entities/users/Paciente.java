package com.escom.backend.domain.entities.users;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente {
  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "id")
  private Usuario usuario;

  private String matricula;
  private String curp;

  // == SETTERS ==

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }

  public void setCurp(String curp) {
    this.curp = curp;
  }

  // == GETTERS ==
  public UUID getId() {
    return this.id;
  }

  public Usuario getUsuario() {
    return this.usuario;
  }

  public String getMatricula() {
    return this.matricula;
  }

  public String getCurp() {
    return this.curp;
  }
}

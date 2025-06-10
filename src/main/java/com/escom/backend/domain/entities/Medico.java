package com.escom.backend.domain.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Usuario usuario;

    private String especialidad;
    private String clinica;
    private String cedula;
    private String telefono;

    // SETTERS
    public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
    }
    public void setEspecialidad(String especialidad) {
      this.especialidad = especialidad;
    }
    public void setClinica(String clinica) {
      this.clinica = clinica;
    }
    public void setCedula(String cedula) {
      this.cedula = cedula;
    }
    public void setTelefono(String tel) {
      this.telefono = tel;
    }

    // GETTERS
    public String getEspecialidad() {
      return this.especialidad;
    }

    public String getClinica() {
      return this.clinica;
    }

    public String getCedula() {
      return this.cedula;
    }

    public String getTelefono() {
      return this.telefono;
    }
}

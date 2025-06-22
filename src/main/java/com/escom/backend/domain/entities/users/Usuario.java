package com.escom.backend.domain.entities.users;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
    private String nombre;
    
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private LocalDateTime createdAt = LocalDateTime.now();
    @Column()
    private Boolean keys = false;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Medico medico;
    
    // == Setters ==
    public void setEmail (String email) {
      this.email = email;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
    }

    public void setPassword (String password) {
      this.password = password;
    }

    public void setRol(Rol rol) {
      this.rol = rol;
    }

    public void setKeys() {
      this.keys = true;
    }

    // == Getters ==

    public UUID getId() {
      return this.id;
    }

    public String getEmail() {
      return this.email;
    }

    public String getPassword() {
      return this.password;
    }

    public String getNombre() {
      return this.nombre;
    }

    public LocalDate getFechaNacimiento() {
      return this.fechaNacimiento;
    }

    public Rol getRol () {
      return this.rol;
    }

    public LocalDateTime getCreatedAt() {
      return this.createdAt;
    }

    public boolean getKeys() {
      return this.keys;
    }
}

package com.escom.backend.domain.entities.users;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "farmaceutico")
public class Farmaceutico {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Usuario usuario;

    @Column(nullable = false)
    private String farmacia;
    private String telefono;
    public UUID getId() {
        return id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public String getFarmacia() {
        return farmacia;
    }

    public String getTelefono() {
        return telefono;
    }

    // SETTERS
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    } 
    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setId(UUID id) {
        this.id = id;
    }
}
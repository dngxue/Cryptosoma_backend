package com.escom.backend.domain.entities.security;

import java.time.LocalDateTime;
import java.util.UUID;

import com.escom.backend.domain.entities.users.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "private_key_user", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuario_id"})
})
public class PrivateKeyUser {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "encrypted_key", nullable = false)
    private String encryptedKey;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // == Getters y Setters ==
    public UUID getId() {
        return this.id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEncryptedKey() {
        return this.encryptedKey;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}

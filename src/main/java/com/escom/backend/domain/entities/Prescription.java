package com.escom.backend.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "prescription")

public class Prescription {
  @Id
  @GeneratedValue
  private UUID id;

  @NotNull(message = "El path del archivo es obligatorio")
  private String path;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean surtida = false;

  @NotNull(message = "La firma digital es obligatoria")
  private String signature;

  @NotNull(message = "El hash del archivo es obligatorio")
  private String hash;

  @Column(name = "issue_date")
  @NotNull(message = "La fecha de emisión es obligatoria")
  private LocalDate issueDate;

  @NotNull(message = "La fecha de recepción es obligatoria")
  private LocalDate receivedDate;

  @ManyToOne
  @JoinColumn(name = "paciente_id")
  private Paciente paciente;

  @ManyToOne
  @JoinColumn(name = "medico_id")
  private Medico medico;

  @ManyToOne
  @JoinColumn(name = "farmaceutico_id")
  private Farmaceutico farmaceutico;

  // == SETTERS ==
  public void setPath(String path) {
    this.path = path;
  }
  public void setSignature(String signature) {
    this.signature = signature;
  }
  public void setHash(String hash) {
    this.hash = hash;
  }
  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }
  public void setReceivedDate(LocalDate receivedDate) {
    this.receivedDate = receivedDate;
  }
  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }
  public void setMedico(Medico medico) {
    this.medico = medico;
  }
  public void setFarmaceutico(Farmaceutico farmaceutico) {
    this.farmaceutico = farmaceutico;
  }
  // == GETTERS ==
  public UUID getId() {
    return this.id;
  }
  public String getPath() {
    return this.path;
  }
  public boolean getSurtida() {
    return this.surtida;
  }
  public String getSignature() {
    return this.signature;
  }
  public String getHash() {
    return this.hash;
  }

  public LocalDate getIssueDate() {
    return this.issueDate;
  }
  public LocalDate getReceivedDate() {
    return this.receivedDate;
  }
  public Paciente getPaciente() {
    return this.paciente;
  }
  public Medico getMedico() {
    return this.medico;
  }
  public Farmaceutico getFarmaceutico() {
    return this.farmaceutico;
  }
}

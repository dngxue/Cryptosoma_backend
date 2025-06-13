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
  private String filename;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean surtida = false;

  @NotNull(message = "La firma digital es obligatoria")
  @Column(name = "signature_doctor", nullable = false)
  private String signatureMedico;

  @Column(nullable = true, name = "signature_pharmacist") 
  private String signaturePharmacist;

  @NotNull(message = "La fecha de emisión es obligatoria")
  @Column(name = "fecha_emision", nullable = false)
  private LocalDate fechaEmision;

  @Column(nullable = true, name = "fecha_surtido") // Será null hasta que el farmacéutico la firme
  private LocalDate fechaSurtido;

  @ManyToOne
  @JoinColumn(name = "paciente_id")
  private Paciente paciente;

  @ManyToOne
  @JoinColumn(name = "medico_id")
  private Medico medico;

  @ManyToOne
  @JoinColumn(name = "farmaceutico_id")
  private Farmaceutico farmaceutico;

  public void setFilename(String filename) {
    this.filename = filename;
  }
  public void setSurtida(Boolean surtida) {
    this.surtida = surtida;
  }
  public void setSignatureMedico(String signature_medico) {
    this.signatureMedico = signature_medico;
  }
  public void setSignaturePharmacist(String signature_pharmacist) {
    this.signaturePharmacist = signature_pharmacist;
  }
  public void setFecha_emision(LocalDate fechaEmision) {
    this.fechaEmision = fechaEmision;
  }
  public void setFecha_surtido(LocalDate fechaSurtido) {
    this.fechaSurtido = fechaSurtido;
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
  public UUID getId() {
    return id;
  }
  public String getFilename() {
    return filename;
  }
  public Boolean getSurtida() {
    return surtida;
  }
  public String getSignatureMedico() {
    return signatureMedico;
  }
  public String getSignaturePharmacist() {
    return signaturePharmacist;
  }
  public LocalDate getFecha_emision() {
    return fechaEmision;
  }
  public LocalDate getFecha_surtido() {
    return fechaSurtido;
  }
  public Paciente getPaciente() {
    return paciente;
  }
  public Medico getMedico() {
    return medico;
  }
  public Farmaceutico getFarmaceutico() {
    return farmaceutico;
  }
}

package com.escom.backend.domain.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class PrescriptionDTO { 
  @NotBlank(message = "El ID del médico es obligatorio")
  public String id_medico;

  @NotBlank(message = "El ID del paciente es obligatorio")
  public String id_paciente;

  @NotBlank(message = "La firma es obligatoria")
  public String signature;

  @NotBlank(message = "El hash del archivo es obligatorio")
  public String hash;

  @NotBlank(message = "La fecha de emisión es obligatoria")
  public LocalDate issueDate;
}

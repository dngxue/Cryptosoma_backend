package com.escom.backend.domain.dto.prescription;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonPropertyOrder({
  "id_paciente",
  "id_medico",
  "fechaEmision",
  "diagnostico",
  "tratamiento"
})

public class PrescriptionDTO {
  @NotNull(message = "El ID del paciente es obligatorio")
  public UUID id_paciente;

  @NotNull(message = "El ID del médico es obligatorio")
  public UUID id_medico;

  @NotBlank(message = "El diagnóstico del paciente es obligatorio")
  public String diagnostico;

  @NotNull(message = "El tratamiento es obligatorio")
  public List<MedicamentoDTO> tratamiento;

  @NotBlank(message = "La firma digital del médico es obligatoria")
  public String firma_medico;

  @JsonProperty("fechaEmision")
  @NotNull(message = "La fecha de emisión es obligatoria")
  public LocalDate fecha_emision;
}

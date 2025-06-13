package com.escom.backend.domain.dto.prescription;

import jakarta.validation.constraints.NotBlank;

public class MedicamentoDTO {
  @NotBlank(message = "El nombre del medicamento es obligatorio")
  public String nombre;
  @NotBlank(message = "La dosis del medicamento es obligatoria")
  public String dosis;
  @NotBlank(message = "La frecuencia del medicamento es obligatoria")
  public String frecuencia;
  @NotBlank(message = "La duración del tratamiento es obligatoria")
  public String duracion;
}

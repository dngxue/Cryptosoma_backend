package com.escom.backend.domain.dto.users;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PacienteDTO {
  @Email(message = "El correo electrónico debe de ser válido")
  @NotBlank(message = "El correo electrónico no puede estar vacío")
  public String email;

  @NotBlank(message = "La contraseña no puede estar vacía")
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=~`\\[\\]{}|:;\"'<>,.?/\\\\])[A-Za-z\\d!@#$%^&*()_\\-+=~`\\[\\]{}|:;\"'<>,.?/\\\\]{8,16}$",
    message = "La contraseña debe tener entre 8 y 16 caracteres, con al menos una mayúscula, una minúscula, un número y un carácter especial"
  )
  public String password;

  @NotBlank(message = "El nombre no puede estar vacío")
  public String nombre;

  @NotBlank(message = "El CURP es un campo obligatorio")
  @Size(min = 18, max = 18, message = "El CURP debe tener exactamente 18 caracteres")
  public String curp;

  @NotNull(message = "La fecha de nacimiento es un campo obligatorio")
  @Past(message = "La fecha de nacimiento debe ser pasada a la fecha actual")
  public LocalDate fechaNacimiento;
}

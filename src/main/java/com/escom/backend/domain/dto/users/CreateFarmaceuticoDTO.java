package com.escom.backend.domain.dto.users;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateFarmaceuticoDTO {
  @Email(message = "El correo electrónico debe de ser válido")
  @NotBlank(message = "El correo electrónico no puede estar vacío")
  public String email;

  @NotBlank(message = "La contraseña es campo obligatorio")
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=~`\\[\\]{}|:;\"'<>,.?/\\\\])[A-Za-z\\d!@#$%^&*()_\\-+=~`\\[\\]{}|:;\"'<>,.?/\\\\]{8,16}$",
    message = "La contraseña debe tener entre 8 y 16 caracteres, con al menos una mayúscula, una minúscula, un número y un carácter especial"
  )
  public String password;

  @NotBlank(message = "El nombre es un campo obligatorio")
  public String nombre;

  @NotBlank(message = "El número de teléfono es un campo obligatorio")
  @Size(min = 10, max = 10, message = "El número de teléfono debe tener exactamente 10 caracteres númericos")
  public String tel;

  @NotBlank(message = "El nombre de la farmacia es un campo obligatorio")
  public String farmacia;

  @NotNull(message = "La fecha de nacimiento es un campo obligatorio")
  public LocalDate fechaNacimiento;
}

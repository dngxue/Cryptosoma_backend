package com.escom.backend.domain.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MedicoDTO {
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

  @NotBlank(message = "La especialidad es un campo obligatorio")
  public String especialidad;

  @NotBlank(message = "La clínica es un campo obligatorio") 
  public String clinica;

  @Size(min = 7, max = 10, message = "La cédula debe tener entre 7 a 10 caracteres")
  @NotBlank(message = "La cédula es un campo obligatorio")
  public String cedula;

  @Size(min = 10, max = 10, message = "El número de teléfono debe tener exactamente 10 caracteres numéricos")
  @NotBlank(message = "El número de teléfono es un campo obligatorio")
  public String tel;

  @NotNull(message = "La fecha de nacimiento es un campo obligatorio")
  public LocalDate fechaNacimiento;
}

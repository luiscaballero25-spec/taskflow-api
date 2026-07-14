package com.taskflow.taskflowapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class ProyectoRequestDTO {
    @NotBlank(message = "El nombre del proyecto no puede estar vacío")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El proyecto debe de tener un nombre asignado")
    private Long usuarioId;
}

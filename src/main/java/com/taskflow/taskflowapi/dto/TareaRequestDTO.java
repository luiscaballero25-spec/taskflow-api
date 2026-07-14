package com.taskflow.taskflowapi.dto;

import com.taskflow.taskflowapi.model.EstadoTarea;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TareaRequestDTO {
    @NotBlank(message = "El título de la tarea no puede estar vacío")
    private String titulo;
    private String descripcion;
    private EstadoTarea estado;
    @NotNull(message = "La tarea debe de pertenecer a un proyecto")
    private Long proyectoId;
}

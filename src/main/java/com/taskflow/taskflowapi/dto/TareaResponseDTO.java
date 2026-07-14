package com.taskflow.taskflowapi.dto;

import com.taskflow.taskflowapi.model.EstadoTarea;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TareaResponseDTO {

    private String titulo;
    private String descripcion;
    private EstadoTarea estado;
    private Long id;
    private ProyectoResponseDTO proyecto;
}

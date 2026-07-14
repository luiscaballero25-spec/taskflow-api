package com.taskflow.taskflowapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.Getter;

@Entity
@Getter
@Setter
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotBlank(message = "El título de la tarea no puede estar vacío")
    private String titulo;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;

    @ManyToOne
    @JoinColumn (name = "proyecto_id")
    @NotNull (message = "El proyecto no puede estar vacío")
    private Proyecto proyecto;
}

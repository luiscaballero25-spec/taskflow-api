package com.taskflow.taskflowapi.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter

public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del proyecto no puede estar vacío")
    private String nombre;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "El proyecto debe de tener un usuario asignado")
    private Usuario usuario;

    @OneToMany(mappedBy = "proyecto")
    @JsonIgnore
    private List<Tarea> tareas = new ArrayList<>();
}

package com.taskflow.taskflowapi.model;

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
    private String nombre;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario:id")
    private Usuario usuario;

    @OneToMany(mappedBy = "proyecto")
    private List<Tarea> tareas = new ArrayList<>();
}

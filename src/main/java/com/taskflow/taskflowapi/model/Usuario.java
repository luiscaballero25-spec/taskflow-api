package com.taskflow.taskflowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String nombre;

    private String email;

    @OneToMany(mappedBy = "usuario")
    private List<Proyecto> proyectos = new ArrayList<>();
}

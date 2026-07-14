    package com.taskflow.taskflowapi.model;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.OneToMany;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import com.fasterxml.jackson.annotation.JsonIgnore;
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

        @NotBlank(message = "El nombre no puede estar vacio")
        private String nombre;

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email debe tener un formato válido")
        private String email;


        @OneToMany(mappedBy = "usuario")
        @JsonIgnore
        private List<Proyecto> proyectos = new ArrayList<>();
    }

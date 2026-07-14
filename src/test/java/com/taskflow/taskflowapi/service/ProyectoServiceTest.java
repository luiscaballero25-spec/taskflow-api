package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.dto.ProyectoRequestDTO;
import com.taskflow.taskflowapi.dto.ProyectoResponseDTO;
import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;
import com.taskflow.taskflowapi.model.Proyecto;
import com.taskflow.taskflowapi.model.Usuario;
import com.taskflow.taskflowapi.repository.ProyectoRepository;
import com.taskflow.taskflowapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

public class ProyectoServiceTest {
    @Mock
    private ProyectoRepository proyectoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    @Test
    void crear_deberiaGuardarProyecto_cuandoUsuarioExiste() {
        //Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana García");
        usuario.setEmail("ana@taskflow.com");

        ProyectoRequestDTO dto = new ProyectoRequestDTO();
        dto.setNombre("Rediseño web");
        dto.setDescripcion("Renovar la página corporativa");
        dto.setUsuarioId(1L);

        Proyecto proyectoGuardado = new Proyecto();
        proyectoGuardado.setId(1L);
        proyectoGuardado.setNombre("Rediseño web");
        proyectoGuardado.setDescripcion("Renovar la página corporativa");
        proyectoGuardado.setUsuario(usuario);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(proyectoRepository.save(org.mockito.ArgumentMatchers.any(Proyecto.class))).thenReturn(proyectoGuardado);

        //Act
        ProyectoResponseDTO resultado = proyectoService.crear(dto);

        //Assert
        assertEquals("Rediseño web", resultado.getNombre());
        assertEquals("Ana García", resultado.getUsuario().getNombre());
    }

    @Test
    void crear_deberiaLanzarExcepcion_cuandoUsuarioNoExiste(){
        //Arrange
        ProyectoRequestDTO dto = new ProyectoRequestDTO();
        dto.setNombre("Rediseño web");
        dto.setUsuarioId(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        //Act

        assertThrows(RecursoNoEncontradoException.class, ()->{
            proyectoService.crear(dto);
        });
    }
}

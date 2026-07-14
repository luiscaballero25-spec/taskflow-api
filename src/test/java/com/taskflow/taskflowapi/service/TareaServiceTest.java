package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.dto.TareaRequestDTO;
import com.taskflow.taskflowapi.dto.TareaResponseDTO;
import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;
import com.taskflow.taskflowapi.model.EstadoTarea;
import com.taskflow.taskflowapi.model.Tarea;
import com.taskflow.taskflowapi.model.Usuario;
import com.taskflow.taskflowapi.model.Proyecto;
import com.taskflow.taskflowapi.repository.TareaRepository;
import com.taskflow.taskflowapi.repository.ProyectoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class TareaServiceTest {
    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private TareaService tareaService;

    @Test

    void crear_deberiaGuardarTarea_cuandoProyectoExiste(){
        //Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana García");
        usuario.setEmail("ana@taskflow.com");

        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Rediseño web");
        proyecto.setUsuario(usuario);

        TareaRequestDTO dto = new TareaRequestDTO();
        dto.setTitulo("Diseñar wireframes");
        dto.setEstado(EstadoTarea.PENDIENTE);
        dto.setProyectoId(1L);

        Tarea tareaGuardada = new Tarea();
        tareaGuardada.setId(1L);
        tareaGuardada.setTitulo("Diseñar wireframes");
        tareaGuardada.setEstado(EstadoTarea.PENDIENTE);
        tareaGuardada.setProyecto(proyecto);

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tareaGuardada);

        //Act

        TareaResponseDTO resultado = tareaService.crear(dto);

        //Assert

        assertEquals("Diseñar wireframes", resultado.getTitulo());
        assertEquals(EstadoTarea.PENDIENTE, resultado.getEstado());
        assertEquals("Rediseño web", resultado.getProyecto().getNombre());
        assertEquals("Ana García", resultado.getProyecto().getUsuario().getNombre());
    }

    @Test
    void crear_deberiaLanzarExcepcion_cuandoProyectoNoExiste(){
        //Arrange
        TareaRequestDTO dto = new TareaRequestDTO();
        dto.setTitulo("Diseñar wireframes");
        dto.setProyectoId(99L);

        when(proyectoRepository.findById(99L)).thenReturn(Optional.empty());

        //Act + assert

        assertThrows(RecursoNoEncontradoException.class, () -> tareaService.crear(dto));

    }
}

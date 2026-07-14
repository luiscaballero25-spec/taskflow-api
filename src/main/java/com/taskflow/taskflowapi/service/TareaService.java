package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.dto.ProyectoResponseDTO;
import com.taskflow.taskflowapi.dto.TareaRequestDTO;
import com.taskflow.taskflowapi.dto.TareaResponseDTO;
import com.taskflow.taskflowapi.dto.UsuarioResponseDTO;
import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;
import com.taskflow.taskflowapi.model.Proyecto;
import com.taskflow.taskflowapi.model.Tarea;
import com.taskflow.taskflowapi.repository.ProyectoRepository;
import com.taskflow.taskflowapi.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    public TareaService(TareaRepository tareaRepository, ProyectoRepository proyectoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public List<TareaResponseDTO> listarTodos() {
        return tareaRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    public TareaResponseDTO buscarPorId(Long id) {
        Tarea tarea = buscarEntidadPorId(id);
        return convertirAResponseDTO(tarea);
    }

    public TareaResponseDTO crear(TareaRequestDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Proyecto no encontrado con id " + dto.getProyectoId()));

        Tarea tarea = new Tarea();
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setEstado(dto.getEstado());
        tarea.setProyecto(proyecto);

        Tarea guardada = tareaRepository.save(tarea);
        return convertirAResponseDTO(guardada);
    }

    public TareaResponseDTO actualizar(Long id, TareaRequestDTO dto) {
        Tarea tarea = buscarEntidadPorId(id);

        Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Proyecto no encontrado con id " + dto.getProyectoId()));

        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setEstado(dto.getEstado());
        tarea.setProyecto(proyecto);

        Tarea actualizada = tareaRepository.save(tarea);
        return convertirAResponseDTO(actualizada);
    }

    public void eliminar(Long id) {
        tareaRepository.deleteById(id);
    }

    // --- Métodos auxiliares internos ---

    private Tarea buscarEntidadPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tarea no encontrada con id " + id));
    }

    private TareaResponseDTO convertirAResponseDTO(Tarea tarea) {
        TareaResponseDTO dto = new TareaResponseDTO();
        dto.setId(tarea.getId());
        dto.setTitulo(tarea.getTitulo());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setEstado(tarea.getEstado());

        Proyecto proyecto = tarea.getProyecto();

        ProyectoResponseDTO proyectoDTO = new ProyectoResponseDTO();
        proyectoDTO.setId(proyecto.getId());
        proyectoDTO.setNombre(proyecto.getNombre());
        proyectoDTO.setDescripcion(proyecto.getDescripcion());

        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
        usuarioDTO.setId(proyecto.getUsuario().getId());
        usuarioDTO.setNombre(proyecto.getUsuario().getNombre());
        usuarioDTO.setEmail(proyecto.getUsuario().getEmail());
        proyectoDTO.setUsuario(usuarioDTO);

        dto.setProyecto(proyectoDTO);

        return dto;
    }
}
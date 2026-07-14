package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.dto.ProyectoRequestDTO;
import com.taskflow.taskflowapi.dto.ProyectoResponseDTO;
import com.taskflow.taskflowapi.dto.UsuarioResponseDTO;
import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;
import com.taskflow.taskflowapi.model.Proyecto;
import com.taskflow.taskflowapi.model.Usuario;
import com.taskflow.taskflowapi.repository.ProyectoRepository;
import com.taskflow.taskflowapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service

public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProyectoService(ProyectoRepository proyectoRepository, UsuarioRepository usuarioRepository) {
    this.proyectoRepository = proyectoRepository;
    this.usuarioRepository = usuarioRepository;}

    public List<ProyectoResponseDTO> listarTodos() {
        return proyectoRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    public ProyectoResponseDTO buscarPorId(Long id) {
        Proyecto proyecto = buscarEntidadPorId(id);
        return convertirAResponseDTO(proyecto);
    }

    public ProyectoResponseDTO crear(ProyectoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con el id " + dto.getUsuarioId()));

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setUsuario(usuario);

        Proyecto guardado = proyectoRepository.save(proyecto);
        return convertirAResponseDTO(guardado);
    }

    public ProyectoResponseDTO actualizar(Long id, ProyectoRequestDTO dto) {
        Proyecto proyecto = buscarEntidadPorId(id);

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                        .orElseThrow(()->new RecursoNoEncontradoException("Usuario no encontrado con el id " + dto.getUsuarioId()));
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setUsuario(usuario);

        Proyecto actualizado = proyectoRepository.save(proyecto);
        return convertirAResponseDTO(actualizado);
    }

    public void eliminar(Long id) {
        proyectoRepository.deleteById(id);
    }

    private Proyecto buscarEntidadPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proyecto no encontrado con id " + id));
    }

    private ProyectoResponseDTO convertirAResponseDTO(Proyecto proyecto) {
        ProyectoResponseDTO dto = new ProyectoResponseDTO();
        dto.setId(proyecto.getId());
        dto.setNombre(proyecto.getNombre());
        dto.setDescripcion(proyecto.getDescripcion());

        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
        usuarioDTO.setId(proyecto.getUsuario().getId());
        usuarioDTO.setNombre(proyecto.getUsuario().getNombre());
        usuarioDTO.setEmail(proyecto.getUsuario().getEmail());
        dto.setUsuario(usuarioDTO);

        return dto;
    }



}

package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.dto.UsuarioRequestDTO;
import com.taskflow.taskflowapi.dto.UsuarioResponseDTO;
import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;
import com.taskflow.taskflowapi.model.Usuario;
import com.taskflow.taskflowapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
            this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponseDTO>listarTodos(){
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id){
        Usuario usuario = buscarEntidadPorId(id);
        return convertirAResponseDTO(usuario);
    }

    public UsuarioResponseDTO crear (UsuarioRequestDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(guardado);
    }

    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto){
        Usuario usuario = buscarEntidadPorId(id);
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(actualizado);
    }

    public void eliminar(Long id){
        usuarioRepository.deleteById(id);
    }

    private Usuario buscarEntidadPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id " + id));
    }

    private UsuarioResponseDTO convertirAResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        return dto;
    }
}

package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;
import com.taskflow.taskflowapi.model.Usuario;
import com.taskflow.taskflowapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;

import java.util.List;

@Service

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
            this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario>listarTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id " + id));
    }

    public Usuario crear (Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario datosNuevos){
        Usuario usuario = buscarPorId(id);
        usuario.setNombre(datosNuevos.getNombre());
        usuario.setEmail(datosNuevos.getEmail());
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id){
        usuarioRepository.deleteById(id);
    }
}

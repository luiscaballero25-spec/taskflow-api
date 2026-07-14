package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.dto.UsuarioResponseDTO;
import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;
import com.taskflow.taskflowapi.model.Usuario;
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

 class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void buscarPorId_deberiaDevolverUsuario_cuandoExiste(){
        //Arrange (preparar)
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana García");
        usuario.setEmail("ana@taskflow.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        //Act (actuar)
        UsuarioResponseDTO resultado = usuarioService.buscarPorId(1L);

        //Assert (comprobar)
        assertEquals("Ana García", resultado.getNombre());
        assertEquals("ana@taskflow.com", resultado.getEmail());
    }

    @Test
    void buscarPorId_deberiaLanzarExcepcion_cuandoNoExiste(){
        //Arrange
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        //Act
        assertThrows(RecursoNoEncontradoException.class, ()->{
            usuarioService.buscarPorId(99L);
        });
    }




}
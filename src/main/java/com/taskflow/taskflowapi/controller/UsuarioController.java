package com.taskflow.taskflowapi.controller;

import com.taskflow.taskflowapi.dto.UsuarioResponseDTO;
import com.taskflow.taskflowapi.dto.UsuarioRequestDTO;
import com.taskflow.taskflowapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarTodos(){
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    public UsuarioResponseDTO crear(@Valid @RequestBody UsuarioRequestDTO dto){
        return usuarioService.crear(dto);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto){
        return usuarioService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        usuarioService.eliminar(id);
    }
}

package com.taskflow.taskflowapi.controller;

import com.taskflow.taskflowapi.dto.ProyectoRequestDTO;
import com.taskflow.taskflowapi.dto.ProyectoResponseDTO;
import com.taskflow.taskflowapi.service.ProyectoService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {
    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public List<ProyectoResponseDTO> listarTodos(){
        return  proyectoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ProyectoResponseDTO buscarPorId(@PathVariable Long id){
        return proyectoService.buscarPorId(id);
    }

    @PostMapping
    public ProyectoResponseDTO crear(@Valid @RequestBody ProyectoRequestDTO dto){
        return proyectoService.crear(dto);
    }

    @PutMapping("/{id}")
    public ProyectoResponseDTO actualizar(@PathVariable Long id,@Valid @RequestBody ProyectoRequestDTO dto){
        return proyectoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        proyectoService.eliminar(id);
    }
}

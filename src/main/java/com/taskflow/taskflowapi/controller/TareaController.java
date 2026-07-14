package com.taskflow.taskflowapi.controller;

import com.taskflow.taskflowapi.dto.TareaRequestDTO;
import com.taskflow.taskflowapi.dto.TareaResponseDTO;
import com.taskflow.taskflowapi.service.TareaService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<TareaResponseDTO> listarTodos() {
        return tareaService.listarTodos();
    }

    @GetMapping("/{id}")
    public TareaResponseDTO buscarPorId(@PathVariable Long id) {
        return tareaService.buscarPorId(id);
    }

    @PostMapping
    public TareaResponseDTO crear(@Valid @RequestBody TareaRequestDTO dto) {
        return tareaService.crear(dto);
    }

    @PutMapping("/{id}")
    public TareaResponseDTO actualizar(@PathVariable Long id,@Valid @RequestBody TareaRequestDTO dto) {
        return tareaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
    }
}

package com.taskflow.taskflowapi.controller;

import com.taskflow.taskflowapi.model.Tarea;
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
    public List<Tarea> listarTodos() {
        return tareaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Tarea buscarPorId(@PathVariable Long id) {
        return tareaService.buscarPorId(id);
    }

    @PostMapping
    public Tarea crear(@Valid @RequestBody Tarea tarea) {
        return tareaService.crear(tarea);
    }

    @PutMapping("/{id}")
    public Tarea actualizar(@PathVariable Long id,@Valid @RequestBody Tarea tarea) {
        return tareaService.actualizar(id, tarea);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
    }
}

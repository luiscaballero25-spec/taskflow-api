package com.taskflow.taskflowapi.controller;

import com.taskflow.taskflowapi.model.Proyecto;
import com.taskflow.taskflowapi.service.ProyectoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {
    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public List<Proyecto> listarTodos(){
        return  proyectoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Proyecto buscarPorId(@PathVariable Long id){
        return proyectoService.buscarPorId(id);
    }

    @PostMapping
    public Proyecto crear(@RequestBody Proyecto proyecto){
        return proyectoService.crear(proyecto);
    }

    @PutMapping("/{ID}")
    public Proyecto actualizar(@PathVariable Long id, @RequestBody Proyecto proyecto){
        return proyectoService.actualizar(id, proyecto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        proyectoService.eliminar(id);
    }
}

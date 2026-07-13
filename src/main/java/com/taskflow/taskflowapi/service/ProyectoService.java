package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.model.Proyecto;
import com.taskflow.taskflowapi.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

@Service

public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
    this.proyectoRepository = proyectoRepository;}

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public Proyecto buscarPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id " + id));
    }

    public Proyecto crear(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public Proyecto actualizar(Long id, Proyecto datosNuevos) {
        Proyecto proyecto = buscarPorId(id);
        proyecto.setNombre(datosNuevos.getNombre());
        proyecto.setDescripcion(datosNuevos.getDescripcion());
        proyecto.setUsuario(datosNuevos.getUsuario());
        return proyectoRepository.save(proyecto);
    }

    public void eliminar(Long id) {
        proyectoRepository.deleteById(id);
    }



}

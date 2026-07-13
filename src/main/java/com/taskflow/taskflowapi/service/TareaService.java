package com.taskflow.taskflowapi.service;

import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;
import com.taskflow.taskflowapi.model.Tarea;
import com.taskflow.taskflowapi.repository.TareaRepository;
import org.springframework.stereotype.Service;
import com.taskflow.taskflowapi.exception.RecursoNoEncontradoException;

import java.util.List;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {{
    this.tareaRepository = tareaRepository;}
    }

    public List<Tarea> listarTodos(){
        return tareaRepository.findAll();
    }

    public Tarea buscarPorId(Long id){
        return tareaRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Tarea no encontrada con id " + id));
    }

    public Tarea crear(Tarea tarea){
        return tareaRepository.save(tarea);
    }

    public Tarea actualizar(Long id, Tarea datosNuevos){
        Tarea tarea = buscarPorId(id);
        tarea.setTitulo(datosNuevos.getTitulo());
        tarea.setDescripcion(datosNuevos.getDescripcion());
        tarea.setEstado(datosNuevos.getEstado());
        tarea.setProyecto(datosNuevos.getProyecto());
        return tareaRepository.save(tarea);
    }

    public void eliminar(Long id){
        Tarea tarea = buscarPorId(id);
    }


}

package com.taskflow.taskflowapi.repository;

import com.taskflow.taskflowapi.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
}

package com.taskflow.taskflowapi.repository;

import com.taskflow.taskflowapi.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}

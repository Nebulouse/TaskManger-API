package com.example.taskmanager.repository;

import com.example.taskmanager.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository <Tarefa, Long> {
}

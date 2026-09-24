package com.example.taskmanager.repository;

import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository <Tarefa, Long> {

    List<Tarefa> findByStatus (StatusTarefa status);
}

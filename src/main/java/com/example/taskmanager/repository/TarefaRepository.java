package com.example.taskmanager.repository;

import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t WHERE t.status = :status")
    List<Tarefa> findByStatus(@Param("status") StatusTarefa status);

    @Query("""
                        SELECT t FROM Tarefa t
                        WHERE LOWER(t.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            """)
    List<Tarefa> findByName(
            @Param("titulo") String titulo);
}

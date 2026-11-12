package com.example.taskmanager.Repository;

import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.repository.TarefaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Test
    void deveSalvarUmaTarefa() {

        Tarefa tarefa = new Tarefa(
                "Estudar integração",
                "Aprender testes com JPA",
                StatusTarefa.PENDENTE,
                LocalDate.now());

        Tarefa resultado = tarefaRepository.save(tarefa);

        assertNotNull(resultado.getId());
        assertEquals("Estudar integração", resultado.getTitulo());
    }

    @Test
    void deveBuscarTarefasPorStatus() {

        Tarefa tarefa = new Tarefa(
                "Estudar integração",
                "Aprender testes com JPA",
                StatusTarefa.PENDENTE,
                LocalDate.now());

        Tarefa tarefa2 = new Tarefa(
                "Jogar Bola",
                "Joga la pelota",
                StatusTarefa.PENDENTE,
                LocalDate.now());

        Tarefa tarefa3 = new Tarefa(
                "Tik tok",
                "Tok tik",
                StatusTarefa.CONCLUIDA,
                LocalDate.now()
        );

        tarefaRepository.save(tarefa);
        tarefaRepository.save(tarefa2);
        tarefaRepository.save(tarefa3);


        List<Tarefa> resultado = tarefaRepository.findByStatus(StatusTarefa.PENDENTE);


        assertEquals(2, resultado.size());
        assertTrue(
                resultado.stream()
                        .allMatch(t -> t.getStatus() == StatusTarefa.PENDENTE)
        );
    }


}

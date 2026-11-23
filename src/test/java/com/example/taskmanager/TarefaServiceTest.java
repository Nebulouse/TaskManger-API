package com.example.taskmanager;

import com.example.taskmanager.model.Categoria;
import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.repository.CategoriaRepository;
import com.example.taskmanager.repository.TarefaRepository;
import com.example.taskmanager.service.TarefaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveCriarTarefaComStatusPendente() {

        // Arrange
        Tarefa tarefa = new Tarefa(
                "Estudar Testes",
                "Aprender JUnit e Mockito",
                null,
                null
        );

        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        // Act
        Tarefa resultado = tarefaService.salvarTarefa(tarefa, null);

        // Assert
        assertEquals(StatusTarefa.PENDENTE, resultado.getStatus());
        assertEquals(LocalDate.now(), resultado.getDataCriacao());

        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void deveCriarTarefaAssociadaAUmaCategoria() {

        Categoria categoria = new Categoria("Jogos");


        Tarefa tarefa = new Tarefa(
                "Estudar Testes",
                "Aprender JUnit e Mockito",
                null,
                null);

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa resultado = tarefaService.salvarTarefa(tarefa, 1L);

        assertSame(categoria,resultado.getCategoria());

        verify(categoriaRepository).findById(1L);
        verify(tarefaRepository).save(tarefa);
    }

        @Test
        public void deveLancarExcessaoQuandoCategoriaNaoExistir(){

            Tarefa tarefa = new Tarefa(
                    "Estudar Testes",
                    "Aprender JUnit e Mockito",
                    null,
                    null
            );


            when(categoriaRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                    () -> tarefaService.salvarTarefa(tarefa, 999L));

            verify(categoriaRepository).findById(999L);
            verify(tarefaRepository, never()).save(tarefa);
        }

        @Test
        public void deveRetornarListaVaziaQuandoNaoExistiremTarefasComStatus(){

            Tarefa tarefa = new Tarefa(
                    "Estudar Testes",
                    "Aprender JUnit e Mockito",
                    StatusTarefa.PENDENTE,
                    null
            );

            tarefaRepository.save(tarefa);

            List<Tarefa> resultado = tarefaRepository.findByStatus(StatusTarefa.CONCLUIDA);

            assertTrue(resultado.isEmpty());

        }
}

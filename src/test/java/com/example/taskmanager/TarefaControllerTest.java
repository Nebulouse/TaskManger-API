package com.example.taskmanager;

import com.example.taskmanager.controller.TarefaController;
import com.example.taskmanager.dto.TarefaRequestDTO;
import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TarefaController.class)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TarefaService tarefaService;

    @Test
    void deveRetornar200AoListarTarefas() throws Exception {

        Tarefa tarefa = new Tarefa(
                "Estudar Spring Boot",
                "Aprender testes de Controller",
                StatusTarefa.PENDENTE,
                LocalDate.now()
        );

        when(tarefaService.listarTarefas(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(tarefa)));

        mockMvc.perform(get("/tarefa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].titulo")
                        .value("Estudar Spring Boot"))
                .andExpect(jsonPath("$.content[0].status")
                        .value("PENDENTE"));
    }

    @Test
    void deveRetornar404QuandoTarefaNãoExistir() throws Exception {

        when(tarefaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/tarefa/999"))
                .andExpect(status().isNotFound());

    }

    @Test
    void deveRetornar200QuandoTarefaExistir() throws Exception {

        Tarefa tarefa = new Tarefa(
                "Estudar Spring Boot",
                "Aprender testes de Controller",
                StatusTarefa.PENDENTE,
                LocalDate.now()
        );

        when(tarefaService.buscarPorId(1L))
                .thenReturn(Optional.of(tarefa));

        mockMvc.perform(get("/tarefa/1"))
                .andExpect(status()
                .isOk())
                .andExpect(jsonPath("$.titulo").value("Estudar Spring Boot"));
    }

    @Test
    void deveRetornar201AoCriarTarefa() throws Exception {

        TarefaRequestDTO dto = new TarefaRequestDTO();

        dto.setTitulo("Estudar o planejamento do Vasco");
        dto.setDescricao("O vasco tem um bom futebol");
        dto.setCategoriaId(2L);

        Tarefa tarefaSalva = new Tarefa(
                "Estudar o planejamento do Vasco",
                "O vasco tem um bom futebol",
                StatusTarefa.PENDENTE,
                LocalDate.now()
        );

        when(tarefaService.salvarTarefa(any(Tarefa.class), eq(2L)))
                .thenReturn(tarefaSalva);

        mockMvc.perform(post("/tarefa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo")
                        .value("Estudar o planejamento do Vasco"));
    }

    @Test
    void deveRetornar400QuandoTituloEstiverVazio() throws Exception {

        TarefaRequestDTO dto = new TarefaRequestDTO();

        dto.setTitulo("");
        dto.setDescricao("O vasco tem um bom futebol");
        dto.setCategoriaId(2L);

        mockMvc.perform(post("/tarefa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
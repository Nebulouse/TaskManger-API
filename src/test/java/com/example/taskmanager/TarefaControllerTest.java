package com.example.taskmanager;

import com.example.taskmanager.controller.TarefaController;
import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TarefaController.class)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

}
package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TarefaRequestDTO;
import com.example.taskmanager.dto.TarefaResponseDTO;
import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping()
    public List<TarefaResponseDTO> listarTarefa() {

        List<Tarefa> allTarefas = tarefaService.listarTarefas();

        return allTarefas
                .stream()
                .map(TarefaResponseDTO::converterParaDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable Long id) {

        return tarefaService.buscarPorId(id)
                .map(TarefaResponseDTO::converterParaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status")
    public List<TarefaResponseDTO> buscarPorStatus(@RequestParam StatusTarefa status){
         List<Tarefa> allStatus = tarefaService.buscarPorStatus(status);
         return allStatus
                 .stream()
                 .map(TarefaResponseDTO::converterParaDTO)
                 .toList();
    }

    @PostMapping()
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@Valid @RequestBody TarefaRequestDTO dados) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dados.getTitulo());
        tarefa.setDescricao(dados.getDescricao());

        Tarefa tarefaSalva = tarefaService.salvarTarefa(tarefa);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TarefaResponseDTO.converterParaDTO(tarefaSalva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizar(@PathVariable Long id,@Valid @RequestBody TarefaRequestDTO request) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(request.getTitulo());
        tarefa.setDescricao(request.getDescricao());

        Tarefa tarefaAtulizada = tarefaService.atualizarTarefa(tarefa, id);
        TarefaResponseDTO resposta = TarefaResponseDTO.converterParaDTO(tarefaAtulizada);

        return ResponseEntity.ok(resposta);

    }
}



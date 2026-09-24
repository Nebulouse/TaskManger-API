package com.example.taskmanager.controller;

import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.service.TarefaService;
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
    public List<Tarefa> listarTarefa() {
        return tarefaService.listarTarefas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Tarefa> buscarPorStatus(@PathVariable StatusTarefa status){
        return tarefaService.buscarPorStatus(status);
    }

    @PostMapping()
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.salvarTarefa(tarefa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        Tarefa tarefaAtulizada = tarefaService.atualizarTarefa(tarefa, id);
        return ResponseEntity.ok(tarefaAtulizada);

    }
}



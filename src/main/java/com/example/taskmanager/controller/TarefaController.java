package com.example.taskmanager.controller;

import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaService TarefaService;

    public TarefaController(TarefaService tarefaService){
        this.TarefaService = tarefaService;
    }

    @GetMapping()
    public List<Tarefa> listarTarefa(){
        return TarefaService.listarTarefas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable long id){
        return TarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa){
        return TarefaService.salvarTarefa(tarefa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable long id){
        TarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

}

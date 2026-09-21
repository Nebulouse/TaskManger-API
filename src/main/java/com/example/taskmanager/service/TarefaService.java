package com.example.taskmanager.service;

import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.repository.TarefaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarPorId(long id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }
}

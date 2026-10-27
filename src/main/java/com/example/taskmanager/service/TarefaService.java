package com.example.taskmanager.service;

import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    public List<Tarefa> buscarPorStatus (StatusTarefa status){
        return tarefaRepository.findByStatus(status);
    }
    public Tarefa salvarTarefa(Tarefa tarefa) {
        tarefa.setStatus(StatusTarefa.PENDENTE);
        tarefa.setDataCriacao(LocalDate.now());

        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Tarefa não encontrada"));
        tarefaRepository.delete(tarefa);
    }

    public Tarefa atualizarTarefa(Tarefa tarefaUpdate, long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Tarefa concluída não pode ser atualizada!"
            );
        }

        tarefa.setTitulo(tarefaUpdate.getTitulo());
        tarefa.setDescricao(tarefaUpdate.getDescricao());

        return tarefaRepository.save(tarefa);
    }
}

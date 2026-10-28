package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.TarefaRequestDTO;
import com.example.taskmanager.dto.TarefaResponseDTO;
import com.example.taskmanager.model.Tarefa;

public final class TarefaMapper {
    private TarefaMapper() {
    }

    public static Tarefa toEntity (TarefaRequestDTO dto){
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());

        return tarefa;
    }

    public static TarefaResponseDTO toDTO (Tarefa tarefa){
        return new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getDataCriacao());
    }
}

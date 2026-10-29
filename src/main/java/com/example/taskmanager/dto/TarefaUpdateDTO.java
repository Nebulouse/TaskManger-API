package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TarefaUpdateDTO {


    @NotBlank(message = "O título é obrigatório")
    @Size(max = 100, message = "O título deve conter no máximo 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "A descrição deve conter no máximo 500 caracteres")
    private String descricao;

    public TarefaUpdateDTO() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

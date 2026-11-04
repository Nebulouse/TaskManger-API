package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoriaRequestDTO {

    @NotBlank
    @Size(max = 100, message = "O nome da categoria só pode conter no máximo 100 caracteres")
    private String nome;

    public CategoriaRequestDTO() {
    }

    public CategoriaRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}

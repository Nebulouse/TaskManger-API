package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.CategoriaRequestDTO;
import com.example.taskmanager.dto.CategoriaResponseDTO;
import com.example.taskmanager.model.Categoria;

public class CategoriaMapper {

    public CategoriaMapper() {
    }

    public static Categoria toEntity(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());

        return categoria;
    }

    public static CategoriaResponseDTO toDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome());
    }
}
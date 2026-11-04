package com.example.taskmanager.service;

import com.example.taskmanager.model.Categoria;
import com.example.taskmanager.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id){
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Categoria não encontrada!"));
    }

    public Categoria salvarCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }
}

package com.example.taskmanager.controller;

import com.example.taskmanager.dto.CategoriaRequestDTO;
import com.example.taskmanager.dto.CategoriaResponseDTO;
import com.example.taskmanager.mapper.CategoriaMapper;
import com.example.taskmanager.model.Categoria;
import com.example.taskmanager.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaService.listarCategorias()
                .stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(CategoriaMapper.toDTO(categoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(@Valid @RequestBody CategoriaRequestDTO dados) {

        Categoria categoria = CategoriaMapper.toEntity(dados);
        Categoria salva = categoriaService.salvarCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaMapper.toDTO(salva));

    }

}

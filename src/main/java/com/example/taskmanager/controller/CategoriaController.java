package com.example.taskmanager.controller;

import com.example.taskmanager.model.Categoria;
import com.example.taskmanager.service.CategoriaService;
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

    @GetMapping()
    public List<Categoria> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        Categoria todos = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(todos);
    }

    @PostMapping()
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {

        Categoria salva = categoriaService.salvarCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(salva);

    }

}

package com.example.taskmanager.controller;

import com.example.taskmanager.dto.CategoriaRequestDTO;
import com.example.taskmanager.dto.CategoriaResponseDTO;
import com.example.taskmanager.mapper.CategoriaMapper;
import com.example.taskmanager.model.Categoria;
import com.example.taskmanager.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Categorias",
        description = "Endpoints responsáveis pelo gerenciamento de categorias!"
)

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(
            summary = "Listar categorias",
            description = "Retorna todas as categorias cadastradas"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Categorias encontradas")
    })
    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaService.listarCategorias()
                .stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Listar categoria pelo ID",
            description = "Retorna uma categoria cadastrada por um ID específico."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })

    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(CategoriaMapper.toDTO(categoria));
    }



    @PostMapping
    @Operation(
            summary = "Criar Categoria",
            description = "Cria uma nova categoria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(@Valid @RequestBody CategoriaRequestDTO dados) {

        Categoria categoria = CategoriaMapper.toEntity(dados);
        Categoria salva = categoriaService.salvarCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaMapper.toDTO(salva));

    }

}

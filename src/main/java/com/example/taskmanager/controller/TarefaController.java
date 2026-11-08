package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TarefaRequestDTO;
import com.example.taskmanager.dto.TarefaResponseDTO;
import com.example.taskmanager.dto.TarefaUpdateDTO;
import com.example.taskmanager.mapper.TarefaMapper;
import com.example.taskmanager.model.StatusTarefa;
import com.example.taskmanager.model.Tarefa;
import com.example.taskmanager.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Tarefas",
        description = "Endpoints responsáveis pelo gerenciameno de tarefas"
)
@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping()
    @Operation(
            summary = "Lista Tarefas",
            description = "Lista todas as tarefas existentes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })

    public Page<TarefaResponseDTO> listarTarefas(
            @PageableDefault(size = 10) Pageable pageable) {

        return tarefaService.listarTarefas(pageable)
                .map(TarefaMapper::toDTO);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista uma tarefa pelo ID",
            description = "Retona uma tarefa cadastrada por um ID específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })

    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable Long id) {

        return tarefaService.buscarPorId(id)
                .map(TarefaMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status")
    @Operation(
            summary = "Lista tarefa por status",
            description = "Retona tarefas com o mesmo status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "400")
    })

    public List<TarefaResponseDTO> buscarPorStatus(@RequestParam StatusTarefa status) {
        List<Tarefa> allStatus = tarefaService.buscarPorStatus(status);
        return allStatus
                .stream()
                .map(TarefaMapper::toDTO)
                .toList();
    }

    @PostMapping()
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@Valid @RequestBody TarefaRequestDTO dados) {

        Tarefa tarefa = TarefaMapper.toEntity(dados);
        Long categoriaId = dados.getCategoriaId();

        Tarefa tarefaSalva = tarefaService.salvarTarefa(tarefa, categoriaId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TarefaMapper.toDTO(tarefaSalva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TarefaUpdateDTO dados) {
        Tarefa tarefaUpdate = TarefaMapper.toEntity(dados);

        Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(tarefaUpdate, id);

        return ResponseEntity.ok(TarefaMapper.toDTO(tarefaAtualizada));
    }

    @GetMapping("/titulo")
    public List<TarefaResponseDTO> buscarPorNome(@RequestParam String titulo) {
        List<Tarefa> tarefasEncontradas = tarefaService.buscarPorNome(titulo);

        return tarefasEncontradas
                .stream()
                .map(TarefaMapper::toDTO)
                .toList();
    }
}



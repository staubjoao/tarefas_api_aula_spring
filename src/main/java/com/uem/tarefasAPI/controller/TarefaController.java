package com.uem.tarefasAPI.controller;

import com.uem.tarefasAPI.dto.TarefaDTO;
import com.uem.tarefasAPI.model.Tarefa;
import com.uem.tarefasAPI.service.impl.TarefaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
@CrossOrigin(origins = "*")
public class TarefaController {

    private final TarefaServiceImpl tarefaService;

    public TarefaController(TarefaServiceImpl tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<Tarefa> buscarTodasTarefas() {
        return tarefaService.buscarTodasTarefas();
    }

    @GetMapping("/categoria/{idCategoria}")
    public List<Tarefa> buscarTodasTarefasPorCategoria(@RequestParam Long idCategoria) {
        return tarefaService.buscarTodasTarefasPorCategoria(idCategoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTarefaPorId(@PathVariable("id") Long tarefaId) {
        try {
            Tarefa terefa = tarefaService.buscarTarefaPorId(tarefaId);
            return ResponseEntity.ok(terefa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> salvarNovaTarefa(@RequestBody TarefaDTO tarefaDTO) {
        try {
            Tarefa tarefa = tarefaService.salvarTarefa(tarefaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterarTarefa(
            @PathVariable("id") Long idTarefa,
            @RequestBody TarefaDTO tarefaDTO) {
        try {
            Tarefa tarefa = tarefaService.alterarTarefa(tarefaDTO, idTarefa);
            return ResponseEntity.status(HttpStatus.OK).body(tarefa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirTarefa(@PathVariable("id") Long idTarefa) {
        try {
            tarefaService.deletarTarefa(idTarefa);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/manipular-atividade/{id}")
    public ResponseEntity<?> manipularAtividadeTarefa(@PathVariable("id") Long idTarefa) {
        try {
            tarefaService.manipularAtividadeTarefa(idTarefa);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/concluir/{id}")
    public ResponseEntity<?> concluirTarefa(@PathVariable("id") Long idTarefa) {
        try {
            tarefaService.concluirTarefa(idTarefa);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

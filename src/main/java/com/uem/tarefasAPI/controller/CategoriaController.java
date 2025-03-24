package com.uem.tarefasAPI.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uem.tarefasAPI.dto.CategoriaDTO;
import com.uem.tarefasAPI.model.Categoria;
import com.uem.tarefasAPI.service.impl.CategoriaServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaServiceImpl categoriaService;

    public CategoriaController(CategoriaServiceImpl categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> buscarTodasCategorias() {
        return categoriaService.buscarTodasCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@PathVariable("id") Long categoriaId) {
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorId(categoriaId);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> salvarNovaCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            Categoria categoria = categoriaService.salvarCategoria(categoriaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterarCategoria(
            @PathVariable("id") Long idCategoria,
            @RequestBody CategoriaDTO categoriaDTO) {
        try {
            Categoria categoria = categoriaService.alterarCategoria(categoriaDTO, idCategoria);
            return ResponseEntity.status(HttpStatus.OK).body(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirCategoria(@PathVariable("id") Long idCategoria) {
        try {
            categoriaService.deletarCategoria(idCategoria);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

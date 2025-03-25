package com.uem.tarefasAPI.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uem.tarefasAPI.dto.CategoriaDTO;
import com.uem.tarefasAPI.exception.EntidadeNaoEncontradaException;
import com.uem.tarefasAPI.model.Categoria;
import com.uem.tarefasAPI.repository.CategoriaRepository;
import com.uem.tarefasAPI.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria salvarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        categoria.setNome(categoriaDTO.nome());
        categoria.setDescricao(categoriaDTO.descricao());
        categoria.setDataCriacao(dataHoraAtual);
        categoria.setDataAlteracao(dataHoraAtual);

        return categoriaRepository.save(categoria);
    }

    public Categoria alterarCategoria(CategoriaDTO categoriaDTO, Long idCategoria) {
        Optional<Categoria> opCategoria = categoriaRepository.findById(idCategoria);

        if (opCategoria.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Categoria com id " + idCategoria + " não foi encontrada");
        }

        Categoria categoria = opCategoria.get();

        LocalDateTime dataHoraAtual = LocalDateTime.now();

        categoria.setNome(categoriaDTO.nome());
        categoria.setDescricao(categoriaDTO.descricao());
        categoria.setDataAlteracao(dataHoraAtual);

        return categoriaRepository.save(categoria);
    }

    public Categoria buscarCategoriaPorId(Long idCategoria) {
        Optional<Categoria> opCategoria = categoriaRepository.findById(idCategoria);

        if (opCategoria.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Categoria com id " + idCategoria + " não foi encontrada");
        }

        return opCategoria.get();
    }

    public List<Categoria> buscarTodasCategorias() {
        return categoriaRepository.findAll();
    }

    public void deletarCategoria(Long idCategoria) {
        Optional<Categoria> opCategoria = categoriaRepository.findById(idCategoria);

        if (opCategoria.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Categoria com id " + idCategoria + " não foi encontrada");
        }

        categoriaRepository.delete(opCategoria.get());
    }
    
}

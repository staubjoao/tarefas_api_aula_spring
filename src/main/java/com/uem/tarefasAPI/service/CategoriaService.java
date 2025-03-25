package com.uem.tarefasAPI.service;

import java.util.List;

import com.uem.tarefasAPI.dto.CategoriaDTO;
import com.uem.tarefasAPI.model.Categoria;

public interface CategoriaService {
    
    Categoria salvarCategoria(CategoriaDTO categoriaDTO);

    Categoria alterarCategoria(CategoriaDTO categoriaDTO, Long idCategoria);

    Categoria buscarCategoriaPorId(Long idCategoria);

    List<Categoria> buscarTodasCategorias();

    void deletarCategoria(Long idCategoria);
}

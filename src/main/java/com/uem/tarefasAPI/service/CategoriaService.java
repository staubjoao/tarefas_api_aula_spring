package com.uem.tarefasAPI.service;

import java.util.List;

import com.uem.tarefasAPI.dto.CategoriaDTO;
import com.uem.tarefasAPI.model.Categoria;

public interface CategoriaService {
    
    public Categoria salvarCategoria(CategoriaDTO categoriaDTO);

    public Categoria alterarCategoria(CategoriaDTO categoriaDTO, Long idCategoria);

    public Categoria buscarCategoriaPorId(Long idCategoria);

    public List<Categoria> buscarTodasCategorias();

    public void deletarCategoria(Long idCategoria);
}

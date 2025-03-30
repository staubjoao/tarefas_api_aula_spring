package com.uem.tarefasAPI.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.uem.tarefasAPI.dto.CategoriaDTO;
import com.uem.tarefasAPI.exception.EntidadeNaoEncontradaException;
import com.uem.tarefasAPI.model.Categoria;
import com.uem.tarefasAPI.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    private Categoria categoria;
    private CategoriaDTO categoriaDTO;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Trabalho");
        categoria.setDescricao("Tarefas relacionadas ao trabalho");
        categoria.setDataCriacao(LocalDateTime.now());
        categoria.setDataAlteracao(LocalDateTime.now());

        categoriaDTO = new CategoriaDTO("Trabalho", "Tarefas relacionadas ao trabalho");
    }

    @Test
    void salvarCategoria_DeveRetornarCategoriaSalva() {
        // Arrange
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        // Act
        Categoria resultado = categoriaService.salvarCategoria(categoriaDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(categoriaDTO.nome(), resultado.getNome());
        assertEquals(categoriaDTO.descricao(), resultado.getDescricao());
        assertNotNull(resultado.getDataCriacao());
        assertNotNull(resultado.getDataAlteracao());

        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void alterarCategoria_QuandoCategoriaExiste_DeveRetornarCategoriaAtualizada() {
        // Arrange
        Long id = 1L;
        CategoriaDTO novoDTO = new CategoriaDTO("Pessoal", "Tarefas pessoais");

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        // Act
        Categoria resultado = categoriaService.alterarCategoria(novoDTO, id);

        // Assert
        assertNotNull(resultado);
        assertEquals(novoDTO.nome(), resultado.getNome());
        assertEquals(novoDTO.descricao(), resultado.getDescricao());
        assertNotNull(resultado.getDataAlteracao());

        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void alterarCategoria_QuandoCategoriaNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 99L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> categoriaService.alterarCategoria(categoriaDTO, id));

        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, never()).save(any());
    }

    @Test
    void buscarCategoriaPorId_QuandoCategoriaExiste_DeveRetornarCategoria() {
        // Arrange
        Long id = 1L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        // Act
        Categoria resultado = categoriaService.buscarCategoriaPorId(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(categoria.getId(), resultado.getId());
        assertEquals(categoria.getNome(), resultado.getNome());

        verify(categoriaRepository, times(1)).findById(id);
    }

    @Test
    void buscarCategoriaPorId_QuandoCategoriaNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 99L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> categoriaService.buscarCategoriaPorId(id));

        verify(categoriaRepository, times(1)).findById(id);
    }

    @Test
    void buscarTodasCategorias_DeveRetornarListaDeCategorias() {
        // Arrange
        List<Categoria> categorias = Arrays.asList(categoria, new Categoria());
        when(categoriaRepository.findAll()).thenReturn(categorias);

        // Act
        List<Categoria> resultado = categoriaService.buscarTodasCategorias();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void deletarCategoria_QuandoCategoriaExiste_DeveDeletarCategoria() {
        // Arrange
        Long id = 1L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).delete(categoria);

        // Act
        categoriaService.deletarCategoria(id);

        // Assert
        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).delete(categoria);
    }

    @Test
    void deletarCategoria_QuandoCategoriaNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 99L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> categoriaService.deletarCategoria(id));

        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, never()).delete(any());
    }
}
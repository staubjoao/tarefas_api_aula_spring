package com.uem.tarefasAPI.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.uem.tarefasAPI.dto.TarefaDTO;
import com.uem.tarefasAPI.exception.EntidadeNaoEncontradaException;
import com.uem.tarefasAPI.model.Categoria;
import com.uem.tarefasAPI.model.Tarefa;
import com.uem.tarefasAPI.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TarefaServiceImplTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private CategoriaServiceImpl categoriaService;

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    private Tarefa tarefa;
    private TarefaDTO tarefaDTO;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Trabalho");

        tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Reunião importante");
        tarefa.setDescricao("Preparar apresentação para reunião");
        tarefa.setConcluida(false);
        tarefa.setAtiva(true);
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setDataAlteracao(LocalDateTime.now());
        tarefa.setCategoria(categoria);

        tarefaDTO = new TarefaDTO("Reunião importante", "Preparar apresentação para reunião", 1L);
    }

    @Test
    void salvarTarefa_DeveRetornarTarefaSalva() {
        // Arrange
        when(categoriaService.buscarCategoriaPorId(anyLong())).thenReturn(categoria);
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        Tarefa resultado = tarefaService.salvarTarefa(tarefaDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(tarefaDTO.titulo(), resultado.getTitulo());
        assertEquals(tarefaDTO.descricao(), resultado.getDescricao());
        assertFalse(resultado.getConcluida());
        assertTrue(resultado.getAtiva());
        assertNotNull(resultado.getDataCriacao());
        assertNotNull(resultado.getDataAlteracao());
        assertEquals(categoria, resultado.getCategoria());

        verify(categoriaService, times(1)).buscarCategoriaPorId(tarefaDTO.idCategoria());
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void alterarTarefa_QuandoTarefaExiste_DeveRetornarTarefaAtualizada() {
        // Arrange
        Long id = 1L;
        TarefaDTO novoDTO = new TarefaDTO("Reunião atualizada", "Nova descrição", 2L);
        Categoria novaCategoria = new Categoria();
        novaCategoria.setId(2L);

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
        when(categoriaService.buscarCategoriaPorId(2L)).thenReturn(novaCategoria);
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        Tarefa resultado = tarefaService.alterarTarefa(novoDTO, id);

        // Assert
        assertNotNull(resultado);
        assertEquals(novoDTO.titulo(), resultado.getTitulo());
        assertEquals(novoDTO.descricao(), resultado.getDescricao());
        assertNotNull(resultado.getDataAlteracao());
        assertEquals(novaCategoria, resultado.getCategoria());

        verify(tarefaRepository, times(1)).findById(id);
        verify(categoriaService, times(1)).buscarCategoriaPorId(novoDTO.idCategoria());
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void alterarTarefa_QuandoTarefaNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 99L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> tarefaService.alterarTarefa(tarefaDTO, id));

        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, never()).save(any());
    }

    @Test
    void buscarTarefaPorId_QuandoTarefaExiste_DeveRetornarTarefa() {
        // Arrange
        Long id = 1L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));

        // Act
        Tarefa resultado = tarefaService.buscarTarefaPorId(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(tarefa.getId(), resultado.getId());
        assertEquals(tarefa.getTitulo(), resultado.getTitulo());

        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    void buscarTarefaPorId_QuandoTarefaNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 99L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> tarefaService.buscarTarefaPorId(id));

        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    void buscarTodasTarefas_DeveRetornarListaDeTarefas() {
        // Arrange
        List<Tarefa> tarefas = Arrays.asList(tarefa, new Tarefa());
        when(tarefaRepository.findAll()).thenReturn(tarefas);

        // Act
        List<Tarefa> resultado = tarefaService.buscarTodasTarefas();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void buscarTodasTarefasPorCategoria_DeveRetornarTarefasDaCategoria() {
        // Arrange
        Long idCategoria = 1L;
        List<Tarefa> tarefas = Arrays.asList(tarefa);

        when(categoriaService.buscarCategoriaPorId(idCategoria)).thenReturn(categoria);
        when(tarefaRepository.findByCategoria(categoria)).thenReturn(tarefas);

        // Act
        List<Tarefa> resultado = tarefaService.buscarTodasTarefasPorCategoria(idCategoria);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(tarefa, resultado.get(0));

        verify(categoriaService, times(1)).buscarCategoriaPorId(idCategoria);
        verify(tarefaRepository, times(1)).findByCategoria(categoria);
    }

    @Test
    void deletarTarefa_QuandoTarefaExiste_DeveDeletarTarefa() {
        // Arrange
        Long id = 1L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
        doNothing().when(tarefaRepository).delete(tarefa);

        // Act
        tarefaService.deletarTarefa(id);

        // Assert
        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, times(1)).delete(tarefa);
    }

    @Test
    void deletarTarefa_QuandoTarefaNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 99L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> tarefaService.deletarTarefa(id));

        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, never()).delete(any());
    }

    @Test
    void manipularAtividadeTarefa_QuandoTarefaExiste_DeveAlternarAtividade() {
        // Arrange
        Long id = 1L;
        boolean estadoOriginal = tarefa.getAtiva();

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        tarefaService.manipularAtividadeTarefa(id);

        // Assert
        assertEquals(!estadoOriginal, tarefa.getAtiva());
        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, times(1)).save(tarefa);
    }

    @Test
    void concluirTarefa_QuandoTarefaExiste_DeveMarcarComoConcluida() {
        // Arrange
        Long id = 1L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        tarefaService.concluirTarefa(id);

        // Assert
        assertTrue(tarefa.getConcluida());
        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, times(1)).save(tarefa);
    }
}
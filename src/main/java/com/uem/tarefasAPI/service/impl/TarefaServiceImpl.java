package com.uem.tarefasAPI.service.impl;

import com.uem.tarefasAPI.dto.TarefaDTO;
import com.uem.tarefasAPI.exception.EntidadeNaoEncontradaException;
import com.uem.tarefasAPI.model.Categoria;
import com.uem.tarefasAPI.model.Tarefa;
import com.uem.tarefasAPI.repository.TarefaRepository;
import com.uem.tarefasAPI.service.TarefaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;

    private final CategoriaServiceImpl categoriaService;

    public TarefaServiceImpl(TarefaRepository tarefaRepository, CategoriaServiceImpl categoriaService) {
        this.tarefaRepository = tarefaRepository;
        this.categoriaService = categoriaService;
    }


    public Tarefa salvarTarefa(TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        Categoria categoria = categoriaService.buscarCategoriaPorId(tarefaDTO.idCategoria());

        tarefa.setTitulo(tarefaDTO.titulo());
        tarefa.setDescricao(tarefaDTO.descricao());
        tarefa.setStatus(tarefaDTO.status());
        tarefa.setDataFinal(tarefaDTO.dataFinal());
        tarefa.setConcluida(false);
        tarefa.setAtiva(true);
        tarefa.setDataCriacao(dataHoraAtual);
        tarefa.setDataAlteracao(dataHoraAtual);
        tarefa.setCategoria(categoria);

        return tarefaRepository.save(tarefa);
    }

    public Tarefa alterarTarefa(TarefaDTO tarefaDTO, Long idTarefa) {
        Optional<Tarefa> opTarefa = tarefaRepository.findById(idTarefa);
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        if (opTarefa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Tarefa com id " + idTarefa + " não foi encontrada");
        }

        Tarefa tarefa = opTarefa.get();
        tarefa.setTitulo(tarefaDTO.titulo());
        tarefa.setStatus(tarefaDTO.status());
        tarefa.setDataFinal(tarefaDTO.dataFinal());
        tarefa.setDescricao(tarefaDTO.descricao());
        tarefa.setDataAlteracao(dataHoraAtual);

        if (!Objects.equals(tarefaDTO.idCategoria(), tarefa.getCategoria().getId())) {
            tarefa.setCategoria(categoriaService.buscarCategoriaPorId(tarefaDTO.idCategoria()));
        }

        return tarefaRepository.save(tarefa);
    }

    public Tarefa buscarTarefaPorId(Long idTarefa) {
        Optional<Tarefa> opTarefa = tarefaRepository.findById(idTarefa);

        if (opTarefa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Tarefa com id " + idTarefa + " não foi encontrada");
        }

        return opTarefa.get();
    }

    public List<Tarefa> buscarTodasTarefas() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> buscarTodasTarefasPorCategoria(Long idCategoria) {
        Categoria categoria = categoriaService.buscarCategoriaPorId(idCategoria);

        return tarefaRepository.findByCategoria(categoria);
    }

    public void deletarTarefa(Long idTarefa) {
        Optional<Tarefa> opTarefa = tarefaRepository.findById(idTarefa);

        if (opTarefa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Tarefa com id " + idTarefa + " não foi encontrada");
        }

        Tarefa tarefa = opTarefa.get();

        tarefaRepository.delete(tarefa);
    }

    public void manipularAtividadeTarefa(Long idTarefa) {
        Optional<Tarefa> opTarefa = tarefaRepository.findById(idTarefa);

        if (opTarefa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Tarefa com id " + idTarefa + " não foi encontrada");
        }

        Tarefa tarefa = opTarefa.get();
        tarefa.setAtiva(!tarefa.getAtiva());
        tarefaRepository.save(tarefa);
    }

    public void concluirTarefa(Long idTarefa) {
        Optional<Tarefa> opTarefa = tarefaRepository.findById(idTarefa);

        if (opTarefa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Tarefa com id " + idTarefa + " não foi encontrada");
        }

        Tarefa tarefa = opTarefa.get();
        tarefa.setConcluida(true);
        tarefaRepository.save(tarefa);
    }
}

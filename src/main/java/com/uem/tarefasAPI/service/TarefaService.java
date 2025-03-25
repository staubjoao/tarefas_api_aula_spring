package com.uem.tarefasAPI.service;

import com.uem.tarefasAPI.dto.TarefaDTO;
import com.uem.tarefasAPI.model.Tarefa;

import java.util.List;

public interface TarefaService {
    
    Tarefa salvarTarefa(TarefaDTO tarefaDTO);

    Tarefa alterarTarefa(TarefaDTO tarefaDTO, Long idTarefa);

    Tarefa buscarTarefaPorId(Long idTarefa);

    List<Tarefa> buscarTodasTarefas();

    List<Tarefa> buscarTodasTarefasPorCategoria(Long idCategoria);

    void deletarTarefa(Long idTarefa);

    void manipularAtividadeTarefa(Long idTarefa);

    void concluirTarefa(Long idTarefa);

}

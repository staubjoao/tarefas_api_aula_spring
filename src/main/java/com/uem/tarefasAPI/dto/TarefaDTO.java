package com.uem.tarefasAPI.dto;

import com.uem.tarefasAPI.model.enums.StatusTarefa;

import java.time.LocalDateTime;

public record TarefaDTO(String titulo, String descricao, Long idCategoria, StatusTarefa status, LocalDateTime dataFinal) {
}

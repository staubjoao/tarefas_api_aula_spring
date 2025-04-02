package com.uem.tarefasAPI.dto;

import com.uem.tarefasAPI.model.enums.StatusTarefa;

public record TarefaDTO(String titulo, String descricao, Long idCategoria, StatusTarefa status) {
}

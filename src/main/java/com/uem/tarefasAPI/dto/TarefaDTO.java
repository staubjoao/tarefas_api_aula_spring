package com.uem.tarefasAPI.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uem.tarefasAPI.model.enums.StatusTarefa;

import java.time.LocalDateTime;

public record TarefaDTO(
        String titulo,
        String descricao,
        Long idCategoria,
        StatusTarefa status,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataFinal) {
}

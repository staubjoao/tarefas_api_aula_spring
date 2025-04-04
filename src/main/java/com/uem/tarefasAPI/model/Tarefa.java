package com.uem.tarefasAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uem.tarefasAPI.model.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
@Data
public class Tarefa extends EntidadeBase {

    @Column(nullable = false,  length = 100)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    private Boolean concluida;

    private Boolean ativa;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTarefa status;

    @Column(name = "data_final", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataFinal;
}

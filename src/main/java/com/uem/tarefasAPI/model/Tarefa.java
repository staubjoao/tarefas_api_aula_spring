package com.uem.tarefasAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
@Data
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  length = 100)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    private Boolean concluida;

    private Boolean ativa;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime dataAlteracao;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}

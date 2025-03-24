package com.uem.tarefasAPI.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(name = "data_criacao" ,nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_alteracao" ,nullable = false)
    private LocalDateTime dataAlteracao;

}

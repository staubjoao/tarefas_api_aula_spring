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
public class Categoria extends EntidadeBase {
    
    @Column(nullable = false,  length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

}

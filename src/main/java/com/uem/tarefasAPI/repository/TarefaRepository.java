package com.uem.tarefasAPI.repository;

import com.uem.tarefasAPI.model.Categoria;
import com.uem.tarefasAPI.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByCategoria(Categoria categoria);
}

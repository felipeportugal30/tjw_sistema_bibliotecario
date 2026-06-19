package com.sistema.bibliotecario.livro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.bibliotecario.livro.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

    List<Livro> findByDisponivel(Boolean disponivel);

} 

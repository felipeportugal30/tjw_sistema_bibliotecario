package com.sistema.bibliotecario.autor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.bibliotecario.autor.model.Autor;

public interface AutorRepository  extends JpaRepository<Autor, Long>{
    
} 

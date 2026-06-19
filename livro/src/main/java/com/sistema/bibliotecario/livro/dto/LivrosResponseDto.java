package com.sistema.bibliotecario.livro.dto;

import java.util.List;

import com.sistema.bibliotecario.livro.model.Livro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LivrosResponseDto {
    private String menssagem;
    private List<Livro> livros;
}

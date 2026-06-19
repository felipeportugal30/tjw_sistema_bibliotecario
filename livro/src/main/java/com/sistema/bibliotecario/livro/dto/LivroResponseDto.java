package com.sistema.bibliotecario.livro.dto;

import com.sistema.bibliotecario.livro.model.Livro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LivroResponseDto {
    private String menssagem;
    private Livro livro;
}

package com.sistema.bibliotecario.autor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AtualizarAutorRequestDto {
    private String nome;
    private String nacionalidade;
    private Integer anoNascimento;
}

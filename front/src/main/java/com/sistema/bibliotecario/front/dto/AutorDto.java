package com.sistema.bibliotecario.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutorDto {
    private Long id;
    private String nome;
    private String nacionalidade;
    private Integer anoNascimento;
}

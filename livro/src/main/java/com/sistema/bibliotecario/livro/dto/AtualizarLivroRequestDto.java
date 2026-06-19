package com.sistema.bibliotecario.livro.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AtualizarLivroRequestDto {
    private String titulo;
    private String genero;
    private Integer anoPublicacao;
    private Boolean disponivel;
    private Long autorId;
}

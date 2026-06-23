package com.sistema.bibliotecario.front.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LivroDto {
    private Long id;
    private String titulo;
    private String genero;
    private Integer anoPublicacao;
    private Boolean disponivel;
    private Long autorId;
}

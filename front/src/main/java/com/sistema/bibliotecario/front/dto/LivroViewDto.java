package com.sistema.bibliotecario.front.dto;

import lombok.Getter;

@Getter
public class LivroViewDto {

    private final Long id;
    private final String titulo;
    private final String genero;
    private final Integer anoPublicacao;
    private final Boolean disponivel;
    private final Long autorId;
    private final String nomeAutor;

    public LivroViewDto(LivroDto livro, String nomeAutor) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.genero = livro.getGenero();
        this.anoPublicacao = livro.getAnoPublicacao();
        this.disponivel = livro.getDisponivel();
        this.autorId = livro.getAutorId();
        this.nomeAutor = nomeAutor;
    }
}

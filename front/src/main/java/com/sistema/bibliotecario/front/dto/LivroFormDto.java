package com.sistema.bibliotecario.front.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LivroFormDto {

    @NotBlank(message = "Título não pode ser vazio")
    @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
    private String titulo;

    @NotBlank(message = "Gênero não pode ser vazio")
    private String genero;

    @NotNull(message = "Ano de publicação é obrigatório")
    @Positive(message = "Ano de publicação deve ser positivo")
    private Integer anoPublicacao;

    private Boolean disponivel = true;

    @NotNull(message = "Selecione um autor")
    private Long autorId;

    public LivroFormDto(LivroDto livro) {
        this.titulo = livro.getTitulo();
        this.genero = livro.getGenero();
        this.anoPublicacao = livro.getAnoPublicacao();
        this.disponivel = livro.getDisponivel();
        this.autorId = livro.getAutorId();
    }
}

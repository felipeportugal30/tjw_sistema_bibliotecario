package com.sistema.bibliotecario.livro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LivroRequestDto {
    @NotBlank
    private String titulo;

    @NotBlank
    private String genero;

    @Positive
    @NonNull
    private Integer anoPublicacao;

    private Boolean disponivel = true;

    @NotNull
    private Long autorId;
}

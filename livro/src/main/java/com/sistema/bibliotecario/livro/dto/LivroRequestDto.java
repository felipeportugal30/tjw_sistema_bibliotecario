package com.sistema.bibliotecario.livro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LivroRequestDto {

    @NotBlank(message = "não pode ser vazio")
    @Size(max = 150, message = "deve ter no máximo 150 caracteres")
    private String titulo;

    @NotBlank(message = "não pode ser vazio")
    private String genero;

    @NotNull(message = "é obrigatório")
    @Positive(message = "deve ser um valor positivo")
    private Integer anoPublicacao;

    @NotNull(message = "é obrigatório")
    private Boolean disponivel;

    @NotNull(message = "é obrigatório")
    private Long autorId;
}

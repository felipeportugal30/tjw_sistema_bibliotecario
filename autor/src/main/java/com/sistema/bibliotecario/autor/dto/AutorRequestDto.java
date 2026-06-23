package com.sistema.bibliotecario.autor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AutorRequestDto {

    @NotBlank(message = "não pode ser vazio")
    private String nome;

    @NotBlank(message = "não pode ser vazio")
    private String nacionalidade;

    @NotNull(message = "é obrigatório")
    @Positive(message = "deve ser um valor positivo")
    private Integer anoNascimento;

}

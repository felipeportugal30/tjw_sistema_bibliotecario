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
    
    @NotBlank
    private String nome;

    @NotBlank
    private String nacionalidade;

    @NotNull
    @Positive
    private Integer anoNascimento;

}

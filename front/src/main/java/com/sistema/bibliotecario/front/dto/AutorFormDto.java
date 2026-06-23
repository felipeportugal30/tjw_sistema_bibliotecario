package com.sistema.bibliotecario.front.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutorFormDto {

    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "Nacionalidade não pode ser vazia")
    private String nacionalidade;

    @NotNull(message = "Ano de nascimento é obrigatório")
    @Positive(message = "Ano de nascimento deve ser positivo")
    private Integer anoNascimento;

    public AutorFormDto(AutorDto autor) {
        this.nome = autor.getNome();
        this.nacionalidade = autor.getNacionalidade();
        this.anoNascimento = autor.getAnoNascimento();
    }
}

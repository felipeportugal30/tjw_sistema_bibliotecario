package com.sistema.bibliotecario.autor.dto;

import com.sistema.bibliotecario.autor.model.Autor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AutorResponseDto {
    private String menssagem;
    private Autor autor;
}

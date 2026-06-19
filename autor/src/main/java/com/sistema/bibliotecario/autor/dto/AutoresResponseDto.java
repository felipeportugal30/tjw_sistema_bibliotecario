package com.sistema.bibliotecario.autor.dto;

import java.util.List;

import com.sistema.bibliotecario.autor.model.Autor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AutoresResponseDto {
    private String menssagem;
    private List<Autor> autores;
}

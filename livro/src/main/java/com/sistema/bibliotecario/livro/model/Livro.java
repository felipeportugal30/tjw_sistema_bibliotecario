package com.sistema.bibliotecario.livro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "livro")
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotBlank
    @Column(nullable = false, length = 60)
    private String genero;

    @Positive
    @Column(nullable = false)
    private Integer anoPublicacao;

    @Column(nullable = false)
    private Boolean disponivel = true;

    @Column(nullable = false)
    private Long autorId;
}
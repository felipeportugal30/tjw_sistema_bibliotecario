package com.sistema.bibliotecario.livro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.bibliotecario.livro.dto.AtualizarLivroRequestDto;
import com.sistema.bibliotecario.livro.dto.LivroRequestDto;
import com.sistema.bibliotecario.livro.dto.LivroResponseDto;
import com.sistema.bibliotecario.livro.dto.LivrosResponseDto;
import com.sistema.bibliotecario.livro.service.LivroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("livro")
@RequiredArgsConstructor
public class LivroController {
    
    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroResponseDto> registrarLivro(@RequestBody @Valid LivroRequestDto request) {
        return ResponseEntity.status(201).body(livroService.cadastrarLivro(request));
    }

    @GetMapping
    public ResponseEntity<LivrosResponseDto> listarLivros(@RequestParam(required = false) Boolean disponivel) {
        return ResponseEntity.status(200).body(livroService.listarLivros(disponivel));
    }

    @GetMapping("/{livroId}")
    public ResponseEntity<LivroResponseDto> listarLivroId(@PathVariable Long livroId) {
        return ResponseEntity.status(200).body(livroService.listarLivroId(livroId));
    }

    @PutMapping("/{livroId}")
    public ResponseEntity<LivroResponseDto> atualizarLivroId(@PathVariable Long livroId, @RequestBody @Valid AtualizarLivroRequestDto request) {
        return ResponseEntity.status(200).body(livroService.atualizarLivro(livroId, request));
    }

    @DeleteMapping("/{livroId}")
    public ResponseEntity<String> atualizarLivroId(@PathVariable Long livroId) {
        return ResponseEntity.status(204).body(livroService.deletarLivroId(livroId));
    }
}

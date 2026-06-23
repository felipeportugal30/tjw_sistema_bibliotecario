package com.sistema.bibliotecario.livro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.sistema.bibliotecario.livro.dto.LivroRequestDto;
import com.sistema.bibliotecario.livro.model.Livro;
import com.sistema.bibliotecario.livro.service.LivroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> registrarLivro(@RequestBody @Valid LivroRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.cadastrarLivro(request));
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros(@RequestParam(required = false) Boolean disponivel) {
        return ResponseEntity.ok(livroService.listarLivros(disponivel));
    }

    @GetMapping("/{livroId}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long livroId) {
        return ResponseEntity.ok(livroService.buscarPorId(livroId));
    }

    @PutMapping("/{livroId}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long livroId, @RequestBody @Valid LivroRequestDto request) {
        return ResponseEntity.ok(livroService.atualizarLivro(livroId, request));
    }

    @DeleteMapping("/{livroId}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long livroId) {
        livroService.deletarLivro(livroId);
        return ResponseEntity.noContent().build();
    }
}

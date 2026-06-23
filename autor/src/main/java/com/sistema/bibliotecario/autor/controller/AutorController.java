package com.sistema.bibliotecario.autor.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.sistema.bibliotecario.autor.dto.AutorRequestDto;
import com.sistema.bibliotecario.autor.model.Autor;
import com.sistema.bibliotecario.autor.service.AutorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<Autor> criarAutor(@RequestBody @Valid AutorRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.criarAutor(request));
    }

    @GetMapping
    public ResponseEntity<List<Autor>> listarAutores() {
        return ResponseEntity.ok(autorService.listarAutores());
    }

    @GetMapping("/{autorId}")
    public ResponseEntity<Autor> buscarAutorPorId(@PathVariable Long autorId) {
        return ResponseEntity.ok(autorService.buscarPorId(autorId));
    }

    @PutMapping("/{autorId}")
    public ResponseEntity<Autor> atualizarAutor(@PathVariable Long autorId, @RequestBody @Valid AutorRequestDto request) {
        return ResponseEntity.ok(autorService.atualizarAutor(autorId, request));
    }

    @DeleteMapping("/{autorId}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long autorId) {
        autorService.deletarAutor(autorId);
        return ResponseEntity.noContent().build();
    }
}

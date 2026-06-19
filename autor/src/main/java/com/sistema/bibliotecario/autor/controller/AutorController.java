package com.sistema.bibliotecario.autor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.bibliotecario.autor.dto.AtualizarAutorRequestDto;
import com.sistema.bibliotecario.autor.dto.AutorRequestDto;
import com.sistema.bibliotecario.autor.dto.AutorResponseDto;
import com.sistema.bibliotecario.autor.dto.AutoresResponseDto;
import com.sistema.bibliotecario.autor.service.AutorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {
    
    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDto> criarAutor(@RequestBody @Valid AutorRequestDto request) {
        return ResponseEntity.status(201).body(autorService.criarAutor(request));
    }

    @GetMapping
    public ResponseEntity<AutoresResponseDto> listarAutores() {
        return ResponseEntity.status(200).body(autorService.listarAutores());
    }

    @GetMapping("/{autorId}")
    public ResponseEntity<AutorResponseDto> listarAutorId(@PathVariable Long autorId) {
        return ResponseEntity.status(200).body(autorService.listarAutorId(autorId));
    }

    @PutMapping("/{autorId}")
    public ResponseEntity<AutorResponseDto> atualizarAutor(@PathVariable Long autorId, @RequestBody @Valid AtualizarAutorRequestDto request) {
        return ResponseEntity.status(200).body(autorService.atualizarAutorId(autorId, request));
    }

    @DeleteMapping("/{autorId}")
    public ResponseEntity<AutorResponseDto> deletarAutorId(@PathVariable Long autorId) {
        return ResponseEntity.status(204).body(autorService.deletarAutorId(autorId));
    }
}

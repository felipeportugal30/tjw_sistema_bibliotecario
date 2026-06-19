package com.sistema.bibliotecario.livro.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistema.bibliotecario.livro.dto.AtualizarLivroRequestDto;
import com.sistema.bibliotecario.livro.dto.LivroRequestDto;
import com.sistema.bibliotecario.livro.dto.LivroResponseDto;
import com.sistema.bibliotecario.livro.dto.LivrosResponseDto;
import com.sistema.bibliotecario.livro.model.Livro;
import com.sistema.bibliotecario.livro.repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LivroService {
    
    private final LivroRepository livroRepository;

    public LivroResponseDto cadastrarLivro(LivroRequestDto request) {
        Livro livro = new Livro();
        livro.setAnoPublicacao(request.getAnoPublicacao());
        livro.setAutorId(request.getAutorId());
        livro.setDisponivel(request.getDisponivel());
        livro.setGenero(request.getGenero());
        livro.setTitulo(request.getTitulo());

        livroRepository.save(livro);

        return new LivroResponseDto("Livro cadastrado com sucesso.", livro);
    }

    public LivrosResponseDto listarLivros(Boolean disponivel) {
        List<Livro> livros;

        if (disponivel == null) {
            livros = livroRepository.findAll();
        } else {
            livros = livroRepository.findByDisponivel(disponivel);
        }

        return new LivrosResponseDto("Livros listados com sucesso", livros);
    }

    public LivroResponseDto listarLivroId(Long livroId) {
        Livro livro = livroRepository.findById(livroId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + livroId));

        return new LivroResponseDto("Livro listado com sucesso", livro);
    }

    public LivroResponseDto atualizarLivro(Long livroId, AtualizarLivroRequestDto request) {
        Livro livroModificado = livroRepository.findById(livroId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + livroId));

        if(request.getDisponivel() != null) livroModificado.setDisponivel(request.getDisponivel());
        if(request.getAnoPublicacao() != null) livroModificado.setAnoPublicacao(request.getAnoPublicacao());
        if(request.getAutorId() != null) livroModificado.setAutorId(request.getAutorId());
        if(request.getGenero() != null) livroModificado.setGenero(request.getGenero());
        if(request.getTitulo() != null) livroModificado.setTitulo(request.getTitulo());

        livroRepository.save(livroModificado);

        return new LivroResponseDto("Livro atualizado com sucesso", livroModificado);
    }

    public String deletarLivroId(Long livroId) {
        Livro livro = livroRepository.findById(livroId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado: " + livroId));

        livroRepository.delete(livro);

        return "Livro deletado com sucesso." + livroId;
    }
}

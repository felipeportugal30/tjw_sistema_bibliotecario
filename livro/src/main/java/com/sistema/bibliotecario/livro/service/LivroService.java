package com.sistema.bibliotecario.livro.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistema.bibliotecario.livro.dto.LivroRequestDto;
import com.sistema.bibliotecario.livro.model.Livro;
import com.sistema.bibliotecario.livro.repository.LivroRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro cadastrarLivro(LivroRequestDto request) {
        Livro livro = new Livro();
        livro.setTitulo(request.getTitulo());
        livro.setGenero(request.getGenero());
        livro.setAnoPublicacao(request.getAnoPublicacao());
        livro.setDisponivel(request.getDisponivel());
        livro.setAutorId(request.getAutorId());

        return livroRepository.save(livro);
    }

    public List<Livro> listarLivros(Boolean disponivel) {
        if (disponivel == null) {
            return livroRepository.findAll();
        }

        return livroRepository.findByDisponivel(disponivel);
    }

    public Livro buscarPorId(Long livroId) {
        return livroRepository.findById(livroId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + livroId));
    }

    public Livro atualizarLivro(Long livroId, LivroRequestDto request) {
        Livro livro = buscarPorId(livroId);

        livro.setTitulo(request.getTitulo());
        livro.setGenero(request.getGenero());
        livro.setAnoPublicacao(request.getAnoPublicacao());
        livro.setDisponivel(request.getDisponivel());
        livro.setAutorId(request.getAutorId());

        return livroRepository.save(livro);
    }

    public void deletarLivro(Long livroId) {
        Livro livro = buscarPorId(livroId);

        livroRepository.delete(livro);
    }
}

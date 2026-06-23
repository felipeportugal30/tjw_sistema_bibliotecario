package com.sistema.bibliotecario.autor.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistema.bibliotecario.autor.dto.AutorRequestDto;
import com.sistema.bibliotecario.autor.model.Autor;
import com.sistema.bibliotecario.autor.repository.AutorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    public Autor criarAutor(AutorRequestDto request) {
        Autor autor = new Autor();
        autor.setNome(request.getNome());
        autor.setNacionalidade(request.getNacionalidade());
        autor.setAnoNascimento(request.getAnoNascimento());

        return autorRepository.save(autor);
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Long autorId) {
        return autorRepository.findById(autorId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado: " + autorId));
    }

    public Autor atualizarAutor(Long autorId, AutorRequestDto request) {
        Autor autor = buscarPorId(autorId);

        autor.setNome(request.getNome());
        autor.setNacionalidade(request.getNacionalidade());
        autor.setAnoNascimento(request.getAnoNascimento());

        return autorRepository.save(autor);
    }

    public void deletarAutor(Long autorId) {
        Autor autor = buscarPorId(autorId);

        autorRepository.delete(autor);
    }
}

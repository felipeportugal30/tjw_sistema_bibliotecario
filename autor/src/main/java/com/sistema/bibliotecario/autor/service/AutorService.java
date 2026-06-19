package com.sistema.bibliotecario.autor.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistema.bibliotecario.autor.dto.AtualizarAutorRequestDto;
import com.sistema.bibliotecario.autor.dto.AutorRequestDto;
import com.sistema.bibliotecario.autor.dto.AutorResponseDto;
import com.sistema.bibliotecario.autor.dto.AutoresResponseDto;
import com.sistema.bibliotecario.autor.model.Autor;
import com.sistema.bibliotecario.autor.repository.AutorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {
    
    private final AutorRepository autorRepository;

    public AutorResponseDto criarAutor(AutorRequestDto request) {
        Autor autor = new Autor();
        autor.setNome(request.getNome());
        autor.setNacionalidade(request.getNacionalidade());
        autor.setAnoNascimento(request.getAnoNascimento());

        Autor autorSalvo = autorRepository.save(autor);

        return new AutorResponseDto("Autores listados com sucesso.", autorSalvo);
    }

    public AutoresResponseDto listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        return new AutoresResponseDto("Autores listados com sucesso.", autores);
    }

    public AutorResponseDto listarAutorId(Long autorId) {
        Autor autor = autorRepository.findById(autorId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado: " + autorId));

        return new AutorResponseDto("Autor encontrado com sucesso.", autor);
    }

    public AutorResponseDto atualizarAutorId(Long autorId, AtualizarAutorRequestDto request) {
        Autor autorModificado = autorRepository.findById(autorId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado: " + autorId));

        if (request.getNome() != null) autorModificado.setNome(request.getNome());
        if (request.getNacionalidade() != null) autorModificado.setNacionalidade(request.getNacionalidade());
        if (request.getAnoNascimento() != null) autorModificado.setAnoNascimento(request.getAnoNascimento());

        Autor autorSalvo = autorRepository.save(autorModificado);

        return new AutorResponseDto("Autor atualizado com sucesso: " + autorId, autorSalvo);
    }

    public AutorResponseDto deletarAutorId(Long autorId) {
        Autor autor = autorRepository.findById(autorId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado: " + autorId));

        autorRepository.delete(autor);

        return new AutorResponseDto("Autor deletado com sucesso.", autor);
    }
}

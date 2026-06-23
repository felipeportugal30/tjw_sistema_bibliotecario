package com.sistema.bibliotecario.front.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.sistema.bibliotecario.front.dto.LivroDto;
import com.sistema.bibliotecario.front.dto.LivroFormDto;
import com.sistema.bibliotecario.front.exception.ErroValidacaoException;
import com.sistema.bibliotecario.front.exception.RecursoNaoEncontradoException;
import com.sistema.bibliotecario.front.exception.ServicoIndisponivelException;

@Component
public class LivroClient {

    private static final String MENSAGEM_INDISPONIVEL = "Serviço de livros está indisponível no momento.";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public LivroClient(@Qualifier("livroRestClient") RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public List<LivroDto> listarLivros(Boolean disponivel) {
        try {
            String uri = UriComponentsBuilder.fromPath("/api/livros")
                .queryParamIfPresent("disponivel", Optional.ofNullable(disponivel))
                .toUriString();

            LivroDto[] livros = restClient.get()
                .uri(uri)
                .retrieve()
                .body(LivroDto[].class);
            return livros == null ? List.of() : Arrays.asList(livros);
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    public Optional<LivroDto> buscarPorId(Long id) {
        try {
            LivroDto livro = restClient.get()
                .uri("/api/livros/{id}", id)
                .retrieve()
                .body(LivroDto.class);
            return Optional.ofNullable(livro);
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    public LivroDto criar(LivroFormDto form) {
        try {
            return restClient.post()
                .uri("/api/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .body(form)
                .retrieve()
                .body(LivroDto.class);
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new ErroValidacaoException("Dados inválidos", extrairCampos(ex));
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    public LivroDto atualizar(Long id, LivroFormDto form) {
        try {
            return restClient.put()
                .uri("/api/livros/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(form)
                .retrieve()
                .body(LivroDto.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNaoEncontradoException("Livro não encontrado: " + id);
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new ErroValidacaoException("Dados inválidos", extrairCampos(ex));
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    public void deletar(Long id) {
        try {
            restClient.delete()
                .uri("/api/livros/{id}", id)
                .retrieve()
                .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNaoEncontradoException("Livro não encontrado: " + id);
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    private Map<String, String> extrairCampos(HttpClientErrorException.BadRequest ex) {
        Map<String, String> campos = new HashMap<>();
        try {
            JsonNode raiz = objectMapper.readTree(ex.getResponseBodyAsString());
            JsonNode camposNode = raiz.get("campos");
            if (camposNode != null) {
                camposNode.properties().forEach(entry -> campos.put(entry.getKey(), entry.getValue().asText()));
            }
        } catch (Exception ignored) {
            // corpo de erro fora do formato esperado; segue com mapa vazio
        }
        return campos;
    }
}

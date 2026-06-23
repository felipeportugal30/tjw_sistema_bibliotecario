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

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.sistema.bibliotecario.front.dto.AutorDto;
import com.sistema.bibliotecario.front.dto.AutorFormDto;
import com.sistema.bibliotecario.front.exception.ErroValidacaoException;
import com.sistema.bibliotecario.front.exception.RecursoNaoEncontradoException;
import com.sistema.bibliotecario.front.exception.ServicoIndisponivelException;

@Component
public class AutorClient {

    private static final String MENSAGEM_INDISPONIVEL = "Serviço de autores está indisponível no momento.";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public AutorClient(@Qualifier("autorRestClient") RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public List<AutorDto> listarAutores() {
        try {
            AutorDto[] autores = restClient.get()
                .uri("/api/autores")
                .retrieve()
                .body(AutorDto[].class);
            return autores == null ? List.of() : Arrays.asList(autores);
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    public Optional<AutorDto> buscarPorId(Long id) {
        try {
            AutorDto autor = restClient.get()
                .uri("/api/autores/{id}", id)
                .retrieve()
                .body(AutorDto.class);
            return Optional.ofNullable(autor);
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    public AutorDto criar(AutorFormDto form) {
        try {
            return restClient.post()
                .uri("/api/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .body(form)
                .retrieve()
                .body(AutorDto.class);
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new ErroValidacaoException("Dados inválidos", extrairCampos(ex));
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    public AutorDto atualizar(Long id, AutorFormDto form) {
        try {
            return restClient.put()
                .uri("/api/autores/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(form)
                .retrieve()
                .body(AutorDto.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNaoEncontradoException("Autor não encontrado: " + id);
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new ErroValidacaoException("Dados inválidos", extrairCampos(ex));
        } catch (RestClientException ex) {
            throw new ServicoIndisponivelException(MENSAGEM_INDISPONIVEL, ex);
        }
    }

    public void deletar(Long id) {
        try {
            restClient.delete()
                .uri("/api/autores/{id}", id)
                .retrieve()
                .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNaoEncontradoException("Autor não encontrado: " + id);
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

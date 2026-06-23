package com.sistema.bibliotecario.front.exception;

import java.util.Map;

public class ErroValidacaoException extends RuntimeException {

    private final Map<String, String> campos;

    public ErroValidacaoException(String message, Map<String, String> campos) {
        super(message);
        this.campos = campos;
    }

    public Map<String, String> getCampos() {
        return campos;
    }
}

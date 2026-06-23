package com.sistema.bibliotecario.front.exception;

public class ServicoIndisponivelException extends RuntimeException {

    public ServicoIndisponivelException(String message, Throwable cause) {
        super(message, cause);
    }
}

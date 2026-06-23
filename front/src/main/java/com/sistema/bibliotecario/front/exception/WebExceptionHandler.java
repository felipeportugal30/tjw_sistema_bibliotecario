package com.sistema.bibliotecario.front.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(ServicoIndisponivelException.class)
    public String tratarServicoIndisponivel(ServicoIndisponivelException ex, Model model) {
        model.addAttribute("mensagemErro", ex.getMessage());
        return "erro";
    }
}

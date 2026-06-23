package com.sistema.bibliotecario.front.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.bibliotecario.front.client.AutorClient;
import com.sistema.bibliotecario.front.dto.AutorFormDto;
import com.sistema.bibliotecario.front.exception.ErroValidacaoException;
import com.sistema.bibliotecario.front.exception.RecursoNaoEncontradoException;
import com.sistema.bibliotecario.front.exception.ServicoIndisponivelException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AutorWebController {

    private final AutorClient autorClient;

    @GetMapping("/autores")
    public String listar(Model model) {
        try {
            model.addAttribute("autores", autorClient.listarAutores());
        } catch (ServicoIndisponivelException ex) {
            model.addAttribute("autores", List.of());
            model.addAttribute("erro", ex.getMessage());
        }
        return "autores/lista";
    }

    @GetMapping("/autores/novo")
    public String novo(Model model) {
        model.addAttribute("autorForm", new AutorFormDto());
        model.addAttribute("modoEdicao", false);
        return "autores/formulario";
    }

    @GetMapping("/autores/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            return autorClient.buscarPorId(id)
                .map(autor -> {
                    model.addAttribute("autorForm", new AutorFormDto(autor));
                    model.addAttribute("modoEdicao", true);
                    model.addAttribute("autorId", id);
                    return "autores/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensagemErro", "Autor não encontrado: " + id);
                    return "redirect:/autores";
                });
        } catch (ServicoIndisponivelException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/autores";
        }
    }

    @PostMapping("/autores")
    public String criar(@Valid @ModelAttribute("autorForm") AutorFormDto form, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicao", false);
            return "autores/formulario";
        }
        try {
            autorClient.criar(form);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Autor cadastrado com sucesso.");
            return "redirect:/autores";
        } catch (ErroValidacaoException ex) {
            aplicarErrosApi(ex, bindingResult);
            model.addAttribute("modoEdicao", false);
            return "autores/formulario";
        } catch (ServicoIndisponivelException ex) {
            model.addAttribute("modoEdicao", false);
            model.addAttribute("erro", ex.getMessage());
            return "autores/formulario";
        }
    }

    @PostMapping("/autores/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("autorForm") AutorFormDto form,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modoEdicao", true);
            model.addAttribute("autorId", id);
            return "autores/formulario";
        }
        try {
            autorClient.atualizar(id, form);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Autor atualizado com sucesso.");
            return "redirect:/autores";
        } catch (ErroValidacaoException ex) {
            aplicarErrosApi(ex, bindingResult);
            model.addAttribute("modoEdicao", true);
            model.addAttribute("autorId", id);
            return "autores/formulario";
        } catch (RecursoNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/autores";
        } catch (ServicoIndisponivelException ex) {
            model.addAttribute("modoEdicao", true);
            model.addAttribute("autorId", id);
            model.addAttribute("erro", ex.getMessage());
            return "autores/formulario";
        }
    }

    @PostMapping("/autores/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            autorClient.deletar(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Autor excluído com sucesso.");
        } catch (RecursoNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        } catch (ServicoIndisponivelException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/autores";
    }

    private void aplicarErrosApi(ErroValidacaoException ex, BindingResult bindingResult) {
        ex.getCampos().forEach((campo, mensagem) -> {
            if (bindingResult.getFieldError(campo) == null) {
                bindingResult.rejectValue(campo, "api", mensagem);
            }
        });
    }
}

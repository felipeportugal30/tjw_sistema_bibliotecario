package com.sistema.bibliotecario.front.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.bibliotecario.front.client.AutorClient;
import com.sistema.bibliotecario.front.client.LivroClient;
import com.sistema.bibliotecario.front.dto.AutorDto;
import com.sistema.bibliotecario.front.dto.LivroDto;
import com.sistema.bibliotecario.front.dto.LivroFormDto;
import com.sistema.bibliotecario.front.dto.LivroViewDto;
import com.sistema.bibliotecario.front.exception.ErroValidacaoException;
import com.sistema.bibliotecario.front.exception.RecursoNaoEncontradoException;
import com.sistema.bibliotecario.front.exception.ServicoIndisponivelException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LivroWebController {

    private final LivroClient livroClient;
    private final AutorClient autorClient;

    @GetMapping("/livros")
    public String listar(@RequestParam(required = false) Boolean disponivel, Model model) {
        try {
            List<LivroDto> livros = livroClient.listarLivros(disponivel);
            model.addAttribute("livros", livros.stream().map(this::paraView).toList());
        } catch (ServicoIndisponivelException ex) {
            model.addAttribute("livros", List.of());
            model.addAttribute("erro", ex.getMessage());
        }
        model.addAttribute("filtroDisponivel", disponivel);
        return "livros/lista";
    }

    @GetMapping("/livros/{id}")
    public String detalhe(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            return livroClient.buscarPorId(id)
                .map(livro -> {
                    model.addAttribute("livro", paraView(livro));
                    return "livros/detalhe";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensagemErro", "Livro não encontrado: " + id);
                    return "redirect:/livros";
                });
        } catch (ServicoIndisponivelException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/livros";
        }
    }

    @GetMapping("/livros/novo")
    public String novo(Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("livroForm", new LivroFormDto());
            model.addAttribute("modoEdicao", false);
            model.addAttribute("autores", autorClient.listarAutores());
            return "livros/formulario";
        } catch (ServicoIndisponivelException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível cadastrar livros: " + ex.getMessage());
            return "redirect:/livros";
        }
    }

    @GetMapping("/livros/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            return livroClient.buscarPorId(id)
                .map(livro -> {
                    model.addAttribute("livroForm", new LivroFormDto(livro));
                    model.addAttribute("modoEdicao", true);
                    model.addAttribute("livroId", id);
                    model.addAttribute("autores", autorClient.listarAutores());
                    return "livros/formulario";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensagemErro", "Livro não encontrado: " + id);
                    return "redirect:/livros";
                });
        } catch (ServicoIndisponivelException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/livros";
        }
    }

    @PostMapping("/livros")
    public String criar(@Valid @ModelAttribute("livroForm") LivroFormDto form, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return formularioComErro(false, null, model);
        }
        try {
            livroClient.criar(form);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro cadastrado com sucesso.");
            return "redirect:/livros";
        } catch (ErroValidacaoException ex) {
            aplicarErrosApi(ex, bindingResult);
            return formularioComErro(false, null, model);
        } catch (ServicoIndisponivelException ex) {
            model.addAttribute("erro", ex.getMessage());
            return formularioComErro(false, null, model);
        }
    }

    @PostMapping("/livros/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("livroForm") LivroFormDto form,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return formularioComErro(true, id, model);
        }
        try {
            livroClient.atualizar(id, form);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro atualizado com sucesso.");
            return "redirect:/livros";
        } catch (ErroValidacaoException ex) {
            aplicarErrosApi(ex, bindingResult);
            return formularioComErro(true, id, model);
        } catch (RecursoNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/livros";
        } catch (ServicoIndisponivelException ex) {
            model.addAttribute("erro", ex.getMessage());
            return formularioComErro(true, id, model);
        }
    }

    @PostMapping("/livros/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            livroClient.deletar(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro excluído com sucesso.");
        } catch (RecursoNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        } catch (ServicoIndisponivelException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/livros";
    }

    private String formularioComErro(boolean modoEdicao, Long id, Model model) {
        model.addAttribute("modoEdicao", modoEdicao);
        if (id != null) {
            model.addAttribute("livroId", id);
        }
        try {
            model.addAttribute("autores", autorClient.listarAutores());
        } catch (ServicoIndisponivelException ex) {
            model.addAttribute("autores", List.of());
        }
        return "livros/formulario";
    }

    private LivroViewDto paraView(LivroDto livro) {
        String nomeAutor;
        try {
            nomeAutor = autorClient.buscarPorId(livro.getAutorId())
                .map(AutorDto::getNome)
                .orElse("Autor removido");
        } catch (ServicoIndisponivelException ex) {
            nomeAutor = "Indisponível";
        }
        return new LivroViewDto(livro, nomeAutor);
    }

    private void aplicarErrosApi(ErroValidacaoException ex, BindingResult bindingResult) {
        ex.getCampos().forEach((campo, mensagem) -> {
            if (bindingResult.getFieldError(campo) == null) {
                bindingResult.rejectValue(campo, "api", mensagem);
            }
        });
    }
}

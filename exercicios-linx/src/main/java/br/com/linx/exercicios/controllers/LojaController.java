package br.com.linx.exercicios.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.linx.exercicios.models.Loja;
import br.com.linx.exercicios.repositories.ILojaRepository;

@Controller
@RequestMapping("/loja")
public class LojaController {

	private static final String CAMINHO_PADRAO = "loja";

	@Autowired
	private ILojaRepository lojaRepository;

	@GetMapping("/configuracoes")
	public String loja(Model model) {
		Optional<Loja> loja = lojaRepository.findFirstByOrderByIdAsc();
		model.addAttribute("loja", loja.isPresent() ? loja.get() : new Loja());
		return caminho("configuracoes");
	}

	@GetMapping("/cadastro")
	public String cadastro(Model model) {
		model.addAttribute("loja", new Loja());
		return caminho("cadastro");
	}

	@PostMapping("/cadastro")
	public String cadastrar(Loja loja, RedirectAttributes redirectAttributes) {
		try {
			// remove as configurções anteriores
			lojaRepository.deleteAll();

			// atualiza com a nova configuração
			lojaRepository.save(loja);
			redirectAttributes.addFlashAttribute("mensagem_loja", "Configurações da loja atualizadas!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem_erro", "Erro ao cadastrar as configurações da loja!");
		}
		return redirecionarCaminho("");
	}

	private static final String caminho(String caminho) {
		return CAMINHO_PADRAO.concat("/").concat(caminho);
	}

	private static final String redirecionarCaminho(String caminho) {
		return "redirect:/";
	}

}

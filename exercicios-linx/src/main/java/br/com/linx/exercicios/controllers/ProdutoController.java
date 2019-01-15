package br.com.linx.exercicios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.linx.exercicios.models.Produto;
import br.com.linx.exercicios.repositories.IProdutoRepository;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	
	private static final String CAMINHO_PADRAO = "produto";

	@Autowired
	private IProdutoRepository produtoRepository;

	@GetMapping
	public String produtos(Model model) {
		model.addAttribute("produtos", produtoRepository.findAll());
		return caminho("produtos");
	}

	@GetMapping("/cadastro")
	public String cadastro(Model model) {
		model.addAttribute("produto", new Produto());
		return caminho("cadastro");
	}

	@PostMapping("/cadastro")
	public String cadastrar(@RequestParam("arquivo") MultipartFile arquivo, Produto produto, RedirectAttributes redirectAttributes) {

		try {
			byte[] foto = arquivo.getBytes();
			produto.setFoto(foto);
			produtoRepository.save(produto);
			redirectAttributes.addFlashAttribute("mensagem", "Produto cadastrado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem_erro", "Erro ao cadastrar o produto!");
		}
		return redirecionarCaminho("produtos");
	}
	
	private static final String caminho(String caminho) {
		return CAMINHO_PADRAO.concat("/").concat(caminho);
	}
	
	private static final String redirecionarCaminho(String caminho) {
		return "redirect:/".concat(caminho);
	}
	
}

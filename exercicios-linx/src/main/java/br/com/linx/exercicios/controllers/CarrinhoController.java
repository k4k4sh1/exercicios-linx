package br.com.linx.exercicios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.linx.exercicios.repositories.ICarrinhoRepository;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

	private static final String CAMINHO_PADRAO = "carrinho";

	@Autowired
	private ICarrinhoRepository carrinhoRepository;

	@GetMapping
	public String carrinho(Model model) {
		model.addAttribute("carrinho", carrinhoRepository.findFirstByOrderByIdAsc().get());
		return caminho("carrinho_compras");
	}

	private static final String caminho(String caminho) {
		return CAMINHO_PADRAO.concat("/").concat(caminho);
	}

}

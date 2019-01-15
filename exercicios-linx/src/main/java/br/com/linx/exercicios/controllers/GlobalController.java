package br.com.linx.exercicios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.linx.exercicios.repositories.ICarrinhoRepository;

@ControllerAdvice(basePackages = "br.com.linx.exercicios.controllers")
public class GlobalController {

	@Autowired
	private ICarrinhoRepository carrinhoRepository;
	
	@ModelAttribute
	public void itensCarrinho(Model model) {
		// Quantidade de itens no carrinho
		model.addAttribute("itens_carrinho", carrinhoRepository.findFirstByOrderByIdAsc().get().getItens().size());
	}
	
}

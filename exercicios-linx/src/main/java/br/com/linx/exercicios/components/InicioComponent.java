package br.com.linx.exercicios.components;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.linx.exercicios.models.Carrinho;
import br.com.linx.exercicios.repositories.ICarrinhoRepository;

@Component
public class InicioComponent {

	@Autowired
	private ICarrinhoRepository carrinhoRepository;

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {

		// Remove algum carrinho existente
		carrinhoRepository.deleteAll();

		// Inicia o carrinho de compras
		Optional<Carrinho> carrinho = carrinhoRepository.findFirstByOrderByIdAsc();
		if (!carrinho.isPresent()) {
			carrinhoRepository.save(new Carrinho());
		}

	}
}

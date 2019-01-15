package br.com.linx.exercicios.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.linx.exercicios.models.Carrinho;

public interface ICarrinhoRepository extends CrudRepository<Carrinho, Long> {
	public Optional<Carrinho> findFirstByOrderByIdAsc();
}

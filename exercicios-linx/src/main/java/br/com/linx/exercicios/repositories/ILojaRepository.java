package br.com.linx.exercicios.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.linx.exercicios.models.Loja;

public interface ILojaRepository extends CrudRepository<Loja, Long> {
	public Optional<Loja> findFirstByOrderByIdAsc();
}

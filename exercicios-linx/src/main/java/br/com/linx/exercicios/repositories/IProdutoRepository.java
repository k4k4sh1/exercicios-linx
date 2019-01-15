package br.com.linx.exercicios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.linx.exercicios.models.Produto;

public interface IProdutoRepository extends CrudRepository<Produto, Long>{
	public List<Produto> findByCustoCompraGreaterThan(double valor);
	@Query("SELECT count(*) FROM Produto AS p WHERE p.custoCompra > 0")
	public long findByQuantidadeProdutosComCustoCompraMaiorQueZero();
}

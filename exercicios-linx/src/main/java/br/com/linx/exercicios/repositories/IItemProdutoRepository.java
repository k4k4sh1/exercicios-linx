package br.com.linx.exercicios.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.linx.exercicios.models.ItemProduto;

public interface IItemProdutoRepository extends CrudRepository<ItemProduto, Long> {
}

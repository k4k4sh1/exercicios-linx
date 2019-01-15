package br.com.linx.exercicios.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.linx.exercicios.models.Carrinho;
import br.com.linx.exercicios.models.ItemProduto;
import br.com.linx.exercicios.models.Loja;
import br.com.linx.exercicios.models.Produto;
import br.com.linx.exercicios.repositories.ICarrinhoRepository;
import br.com.linx.exercicios.repositories.IItemProdutoRepository;
import br.com.linx.exercicios.repositories.ILojaRepository;
import br.com.linx.exercicios.repositories.IProdutoRepository;

@Controller
@RequestMapping("/")
public class VendaController {

	@Autowired
	private IProdutoRepository produtoRepository;

	@Autowired
	private ILojaRepository lojaRepository;

	@Autowired
	private ICarrinhoRepository carrinhoRepository;

	@Autowired
	private IItemProdutoRepository itemProdutoRepository;

	@GetMapping
	public String inicio(Model model) {

		Optional<Loja> loja = lojaRepository.findFirstByOrderByIdAsc();
		List<Produto> produtos = produtoRepository.findByCustoCompraGreaterThan(0);
		long quantidadeProdutos = produtoRepository.findByQuantidadeProdutosComCustoCompraMaiorQueZero();

		if (loja.isPresent()) {
			Loja configuracoes = loja.get();
			double despesasTotais = configuracoes.getDespesasTotais() > 0 ? configuracoes.getDespesasTotais() : 400.0;
			double rateioDespesas = despesasTotais / quantidadeProdutos;
			double margemLucro = configuracoes.getMargemLucro();

			produtos.forEach(produto -> {
				produto.setPrecoVenda((produto.getCustoCompra() + rateioDespesas) * (1 + (margemLucro / 100)));
				produtoRepository.save(produto);
			});
		} else {
			produtos.forEach(produto -> {
				produto.setPrecoVenda(produto.getCustoCompra() + (400.0 / quantidadeProdutos));
				produtoRepository.save(produto);
			});
		}

		model.addAttribute("produtos", produtos);
		return "/inicio";
	}

	@PostMapping("/{id}")
	public String adicionarProdutoCarrinho(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {

		Optional<Produto> produto = produtoRepository.findById(id);

		if (produto.isPresent()) {
			Optional<Carrinho> carrinho = carrinhoRepository.findFirstByOrderByIdAsc();
			if (carrinho.isPresent()) {
				Produto produtoSelecionado = produto.get();
				Carrinho carrinhoSelecionado = carrinho.get();

				if (!verificarExistenciaProdutoNoCarrinho(carrinhoSelecionado.getItens(), produtoSelecionado)) {
					carrinhoSelecionado.getItens()
							.add(itemProdutoRepository.save(new ItemProduto(produtoSelecionado, 1L)));
				}

				carrinhoRepository.save(carrinhoSelecionado);
				redirectAttributes.addFlashAttribute("mensagem",
						"\"" + produtoSelecionado.getNome() + "\" foi adicionado ao carrinho de compras!");
			}
		}

		return "redirect:/";
	}

	private boolean verificarExistenciaProdutoNoCarrinho(List<ItemProduto> itens, Produto produto) {
		for (ItemProduto item : itens) {
			Produto produtoPesquisado = item.getProduto();
			if (produto.getId() == produtoPesquisado.getId()) {
				item.setQuantidade(item.getQuantidade() + 1L);
				return true;
			}
		}
		return false;
	}

}

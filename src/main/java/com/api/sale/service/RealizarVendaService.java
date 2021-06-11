package com.api.sale.service;

import java.time.OffsetDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.api.sale.entity.Produto;
import com.api.sale.entity.ProdutoVenda;
import com.api.sale.entity.Venda;
import com.api.sale.exception.QuantidadeInsuficienteException;

import reactor.core.publisher.Mono;

@Service
public class RealizarVendaService {
	
	@Autowired
	private WebClient webClientProdutos;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private ProdutoVendaService produtoVendaService; 
	
	
	@Transactional
	public Venda realizarVenda(List<ProdutoVenda> produtosVenda) {
		Venda venda = produtosVenda.get(0).getVenda();
		venda = adicionaDetalhesVenda(venda);
		venda = adicionaDetalhesProdutoVenda(produtosVenda, venda);
		
		return venda;		
	}
	
	private Produto atualizarProduto(Produto produto, Integer quantidade) {
		
		Integer quantidadeAtualizada = produto.getQuantidade() - quantidade;
		
		if (quantidadeAtualizada < 0) {
			throw new QuantidadeInsuficienteException();
		}
		
		produto.setQuantidade(quantidadeAtualizada);
		
		Mono<Produto> monoProduto = this.webClientProdutos
				.method(HttpMethod.PUT)
				.uri("/produtos/{produtoId}", produto.getId())
				.body(Mono.just(produto), Produto.class)
				.retrieve()
				.bodyToMono(Produto.class);		
		
		Produto produtoPego = monoProduto.block();
		
		return produtoPego;
	}
	
	private Venda adicionaDetalhesVenda(Venda venda) {
		
		venda.setDataVenda(OffsetDateTime.now());		
		venda = vendaService.salvar(venda);
		
		return venda;
	}
	
	private Venda adicionaDetalhesProdutoVenda(List<ProdutoVenda> produtosVenda, Venda venda) {
		
		Double totalValorVenda = 0.0;
		for (ProdutoVenda item : produtosVenda) {
			Mono<Produto> monoProduto = this.webClientProdutos
					.method(HttpMethod.GET)
					.uri("/produtos/{produtoId}", item.getProduto().getId())
					.retrieve()
					.bodyToMono(Produto.class);
			
			Produto produtoPego = monoProduto.block();
		
			produtoPego = atualizarProduto(produtoPego, item.getQuantidade());
			item.setProduto(produtoPego);
			item.setQuantidade(item.getQuantidade());
			item.setValor(item.getQuantidade() * produtoPego.getPreco());
			item.setVenda(venda);
			
			produtoVendaService.salvar(item);
			totalValorVenda = totalValorVenda + item.getValor();
		}
		return venda;
		
	}
}

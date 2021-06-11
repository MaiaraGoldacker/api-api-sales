package com.api.sale.controller.dto;

import java.time.OffsetDateTime;
import java.util.List;
import com.api.sale.entity.enumerado.FormaPagamentoEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VendaDTO {
	
	
	private Long id;
	private OffsetDateTime dataVenda;
	private FormaPagamentoEnum formaPagamento;
	private List<ProdutoVendaDTO> produtosVenda;
	private Double valorTotalVenda;
	
	private void getCalculaVendaTotal() {
		Double valorTotalVenda = 0.0;
		for (ProdutoVendaDTO VendasEProdutos : produtosVenda) {
			valorTotalVenda = valorTotalVenda + VendasEProdutos.getValor();
		}
		this.valorTotalVenda = valorTotalVenda;
	}

	public List<ProdutoVendaDTO> getProdutosVenda() {
		return produtosVenda;
	}

	public void setProdutosVenda(List<ProdutoVendaDTO> produtosVenda) {
		this.produtosVenda = produtosVenda;
		 getCalculaVendaTotal();
	}
	
	
}

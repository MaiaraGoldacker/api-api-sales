package com.api.sale.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.api.sale.controller.dto.input.ProdutoVendaDTOInput;
import com.api.sale.controller.dto.input.VendaDTOInput;
import com.api.sale.entity.Produto;
import com.api.sale.entity.ProdutoVenda;
import com.api.sale.entity.Venda;

@Component
public class ProdutoVendaDTOToProdutoVenda {

	public List<ProdutoVenda> converte (VendaDTOInput vendaDTO) {
		Venda venda = new Venda();
		List<ProdutoVenda> itensVenda = new ArrayList<>();
		
		venda.setFormaPagamento(vendaDTO.getFormaPagamento());
		for (ProdutoVendaDTOInput item : vendaDTO.getProdutovenda()) {
			ProdutoVenda produtoVenda = new ProdutoVenda();
			Produto produto = new Produto();
			produto.setId(item.getProdutoId());
			produtoVenda.setQuantidade(item.getQuantidadeVenda());
			
			produtoVenda.setProduto(produto);
			produtoVenda.setVenda(venda);
			itensVenda.add(produtoVenda);
			
		}
		
		return itensVenda;
	}
}

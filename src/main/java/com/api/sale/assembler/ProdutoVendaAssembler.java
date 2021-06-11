package com.api.sale.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.sale.controller.dto.ProdutoVendaDTO;
import com.api.sale.entity.ProdutoVenda;

@Component
public class ProdutoVendaAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoVendaDTO toModel(ProdutoVenda produtoVenda) {
		return modelMapper.map(produtoVenda, ProdutoVendaDTO.class);
	}
	
	public List<ProdutoVendaDTO> toCollectionModel(List<ProdutoVenda> produtoVendas){
		return produtoVendas.stream()
				.map(produtoVenda -> toModel(produtoVenda))
				.collect(Collectors.toList());
	}
}

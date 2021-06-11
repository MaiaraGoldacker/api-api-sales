package com.api.sale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.sale.entity.ProdutoVenda;
import com.api.sale.repository.ProdutoVendaRepository;

@Service
public class ProdutoVendaService {

	@Autowired
	private ProdutoVendaRepository produtoVendaRepository;
	
	@Transactional
	public ProdutoVenda salvar(ProdutoVenda produtoVenda) {		
		return produtoVendaRepository.save(produtoVenda);
	}
}

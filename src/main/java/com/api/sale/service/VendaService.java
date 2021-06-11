package com.api.sale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.sale.exception.VendaNaoEncontradaException;
import com.api.sale.entity.Venda;
import com.api.sale.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Transactional
	public Venda salvar(Venda venda) {		
		return vendaRepository.save(venda);
	}
	
	public Venda buscarOuFalhar(Long vendaId) {
		return vendaRepository.findById(vendaId).orElseThrow(
				() -> new VendaNaoEncontradaException(vendaId));
	}
}

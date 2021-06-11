package com.api.sale.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.api.sale.controller.dto.VendaDTO;
import com.api.sale.entity.Venda;

@Component
public class VendaDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public VendaDTO toModel(Venda venda) {
		return modelMapper.map(venda, VendaDTO.class);
	}
	
	public List<VendaDTO> toCollectionModel(List<Venda> vendas){
		return vendas.stream()
				.map(venda -> toModel(venda))
				.collect(Collectors.toList());
	}
}

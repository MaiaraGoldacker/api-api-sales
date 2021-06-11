package com.api.sale.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.api.sale.controller.dto.input.ProdutoDtoInput;
import com.api.sale.entity.Produto;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper ModelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(ProdutoDtoInput.class, Produto.class)
	    .addMappings(mapper -> mapper.skip(Produto::setId));
		
		
		return modelMapper;
	}
}
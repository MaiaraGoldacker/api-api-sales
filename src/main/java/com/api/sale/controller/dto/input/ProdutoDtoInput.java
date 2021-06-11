package com.api.sale.controller.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoDtoInput {
	
	@NotNull
	private Long id;
	
	@NotNull
	private Integer quantidade;	

}

package com.api.sale.controller.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoVendaDTO {

	private Long id;
	private ProdutoDTO produto;
	private Integer quantidade;
	private Double valor;
}

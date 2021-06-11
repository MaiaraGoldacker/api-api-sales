package com.api.sale.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoDTO {
private Long id;
	
	private String nome;
	
	private Double preco;
	
	private String descricao;
	
	private Boolean ativo;
	
	@JsonIgnore
	private Integer quantidade;
	
}

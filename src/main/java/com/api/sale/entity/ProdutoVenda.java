package com.api.sale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name="produto_venda")
public class ProdutoVenda {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=false)
	private Venda venda;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=false)
	private Produto produto;
	
	private Integer quantidade;
	
	@Column(name = "valor_venda")
	private Double valor;
}

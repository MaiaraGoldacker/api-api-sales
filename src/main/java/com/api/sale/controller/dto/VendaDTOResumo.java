package com.api.sale.controller.dto;

import java.time.OffsetDateTime;

import com.api.sale.entity.enumerado.FormaPagamentoEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VendaDTOResumo {

	private Long id;
	private OffsetDateTime dataVenda;
	private FormaPagamentoEnum formaPagamento;
}

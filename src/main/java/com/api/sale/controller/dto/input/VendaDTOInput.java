package com.api.sale.controller.dto.input;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.api.sale.entity.enumerado.FormaPagamentoEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VendaDTOInput {
	
	@NotNull
	private FormaPagamentoEnum formaPagamento;
	
	@Size(min = 1)
	@NotNull
	private List<ProdutoVendaDTOInput> produtovenda= new ArrayList<>();
	
}

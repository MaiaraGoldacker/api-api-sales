package com.api.sale.exception;

public class VendaNaoEncontradaException extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	public VendaNaoEncontradaException(Long idVenda) {
		super("Venda id " + idVenda + " não encontrado");
	}
	
}

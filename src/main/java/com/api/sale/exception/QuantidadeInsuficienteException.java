package com.api.sale.exception;

public class QuantidadeInsuficienteException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public QuantidadeInsuficienteException() {
		super("Não há produto suficiente em estoque para realizar essa venda");
	}
}

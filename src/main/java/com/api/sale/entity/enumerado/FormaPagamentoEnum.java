package com.api.sale.entity.enumerado;

public enum FormaPagamentoEnum {
	
	 	CARTAO_CREDITO(1),
	    CARTAO_DEBITO(2),
	    DINHEIRO(3),
	    PIX(4);
	   

	    FormaPagamentoEnum(Integer id) {
	        this.id = id;
	    }

	    private Integer id;

	    public Integer getId() {
	        return id;
	    }
}

package com.api.sale.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.sale.assembler.ProdutoVendaAssembler;
import com.api.sale.assembler.ProdutoVendaDTOToProdutoVenda;
import com.api.sale.assembler.VendaDTOAssembler;
import com.api.sale.assembler.VendaDTOResumoAssembler;
import com.api.sale.controller.dto.ProdutoVendaDTO;
import com.api.sale.controller.dto.VendaDTO;
import com.api.sale.controller.dto.VendaDTOResumo;
import com.api.sale.controller.dto.input.VendaDTOInput;
import com.api.sale.entity.ProdutoVenda;
import com.api.sale.repository.ProdutoVendaRepository;
import com.api.sale.repository.VendaRepository;
import com.api.sale.service.RealizarVendaService;
import com.api.sale.service.VendaService;


@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private RealizarVendaService realizarVendaService; 
	
	@Autowired
	private VendaDTOAssembler vendaAssembler;
	
	@Autowired
	private ProdutoVendaRepository produtoVendaRepository;
	
	@Autowired
	private ProdutoVendaAssembler produtoVendaAssembler;
	
	@Autowired
	private VendaService vendaService; 
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ProdutoVendaDTOToProdutoVenda produtoVendaDtoToProdutoVenda;
	
	@Autowired
	private VendaDTOResumoAssembler vendaDTOResumoAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendaDTO adicionarVenda(@RequestBody @Valid VendaDTOInput vendaDTOInput) {	
		
		List<ProdutoVenda> listaProdutosVenda = 
				 produtoVendaDtoToProdutoVenda.converte(vendaDTOInput);
		
		VendaDTO vendaDTO =vendaAssembler.toModel( realizarVendaService.realizarVenda(listaProdutosVenda));
		List<ProdutoVendaDTO> vendaPorProdutos =  produtoVendaAssembler.toCollectionModel(
		
		produtoVendaRepository.findByVendaId(vendaDTO.getId()));
		vendaDTO.setProdutosVenda(vendaPorProdutos);
		
		 return	vendaDTO;
		
	}
	
	@GetMapping("/{vendaId}")
	public VendaDTO buscarVenda(@PathVariable Long vendaId) {	
		VendaDTO vendaDTO = vendaAssembler.toModel( vendaService.buscarOuFalhar(vendaId));
		
		List<ProdutoVendaDTO> vendaPorProdutos =  produtoVendaAssembler.toCollectionModel(
				produtoVendaRepository.findByVendaId(vendaDTO.getId()));
		 vendaDTO.setProdutosVenda(vendaPorProdutos);
		
		 return	vendaDTO;
	}
	
	@GetMapping
	public List<VendaDTOResumo> buscarVendas() {
		return  vendaDTOResumoAssembler.toCollectionModel(vendaRepository.findAll());
	}
}

package com.api.sale.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.api.sale.entity.Venda;


@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>{
	
	List<Venda> findByDataVenda(OffsetDateTime dataVenda);

}

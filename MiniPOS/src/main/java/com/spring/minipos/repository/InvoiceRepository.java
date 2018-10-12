package com.spring.minipos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.minipos.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long>{
	
	@Query("SELECT i FROM Invoice i WHERE i.id = ?1")
	Invoice findInvoiceById(long id);
}

package com.spring.minipos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.minipos.entity.Invoice;
import com.spring.minipos.repository.InvoiceRepository;

@Service
public class InvoiceServices {
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	public Invoice save(Invoice invoice) {
		return invoiceRepository.save(invoice);
	}
	
	public List<Invoice> findAll(){
		return invoiceRepository.findAll();
	}
	
	public Invoice findInvoiceById(long id) {
		return invoiceRepository.findInvoiceById(id);
	}
	
}

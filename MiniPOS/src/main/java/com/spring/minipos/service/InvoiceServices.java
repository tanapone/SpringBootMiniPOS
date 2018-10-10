package com.spring.minipos.service;

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
}

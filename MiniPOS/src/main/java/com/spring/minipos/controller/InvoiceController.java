package com.spring.minipos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spring.minipos.entity.Company;
import com.spring.minipos.entity.Invoice;
import com.spring.minipos.entity.InvoiceDetail;
import com.spring.minipos.entity.Message;
import com.spring.minipos.entity.Product;
import com.spring.minipos.service.InvoiceServices;
import com.spring.minipos.service.ProductServices;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvoiceController {

	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	@Autowired
	InvoiceServices invoiceService;
	
	@Autowired
	UserServices userServices;

	@Autowired
	ProductServices productServices;
	
	@PostMapping("create/invoice")
	public String createInvoice(@Valid @RequestBody Invoice invoice,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					Invoice newInvoice = new Invoice();
					newInvoice.setInvoiceDetails(invoice.getInvoiceDetails());
					for(InvoiceDetail invoiceDetails : invoice.getInvoiceDetails()) {
						invoiceDetails.setInvoice(newInvoice);
					}
					newInvoice.setSumPrice(invoice.getSumPrice());
					result = gson.toJson(invoiceService.save(newInvoice));
					
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	
}

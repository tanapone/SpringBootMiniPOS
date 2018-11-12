package com.spring.minipos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.spring.minipos.entity.MessageModel;
import com.spring.minipos.entity.Product;
import com.spring.minipos.repository.InvoiceRepository;
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
			@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					Invoice newInvoice = new Invoice();
					newInvoice.setInvoiceDetails(invoice.getInvoiceDetails());
					newInvoice.setCompany(invoice.getInvoiceDetails().get(0).getProduct().getCompany());
					for (InvoiceDetail invoiceDetails : invoice.getInvoiceDetails()) {
						invoiceDetails.setInvoice(newInvoice);
						
					}
					invoiceService.save(newInvoice);
					result = gson.toJson(new MessageModel("Success."));

				} else {
					result = new Gson().toJson(new MessageModel("No permission."));
				}
			} else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}

	@GetMapping("invoices")
	public String showAllInvoices(@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(invoiceService.findAll());
				} else {
					result = gson.toJson(new MessageModel("No permission."));
				}
			} else {
				result = gson.toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}

	@GetMapping("invoice/{id}")
	public String getInvoiceById(@PathVariable long id,
			@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(invoiceService.findInvoiceById(id));
				} else {
					result = new Gson().toJson(new MessageModel("No permission."));
				}
			} else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}

	@PostMapping("update/invoice")
	public String updateInvoice(@Valid @RequestBody Invoice invoice,
			@RequestParam(value = "authKey", required = false) String authKey) {

		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new MessageModel("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					
					Invoice newInvoice = invoiceService.findInvoiceById(invoice.getId());
					
					for(int i=0;i<newInvoice.getInvoiceDetails().size();i++) {
						//Check if invoice detail is not filled and invoice detail query is not equal invoice detail in
						if(newInvoice.getInvoiceDetails().get(i).getProductInQuantity() != newInvoice.getInvoiceDetails().get(i).getQuantity()
								&& newInvoice.getInvoiceDetails().get(i).getProductInQuantity()!=invoice.getInvoiceDetails().get(i).getProductInQuantity()) {
							//Get product to update
							Product product = productServices.findProductById(newInvoice.getInvoiceDetails().get(i).getProduct().getId());
							//Cal new product qty
							int calQty = invoice.getInvoiceDetails().get(i).getProductInQuantity() - newInvoice.getInvoiceDetails().get(i).getProductInQuantity();
							if(calQty<0) {
								calQty = 0;
							}
							int newProductQty = (product.getProductQty() + calQty);
							
							newInvoice.getInvoiceDetails().get(i).setProductInQuantity(invoice.getInvoiceDetails().get(i).getProductInQuantity());
							product.setProductQty(newProductQty);
							productServices.save(product);
						}
					}
					gson.toJson(invoiceService.save(newInvoice));
					result = gson.toJson(new MessageModel("Success."));

				} else {
					result = new Gson().toJson(new MessageModel("No permission."));
				}
			} else {
				result = new Gson().toJson(new MessageModel("Wrong auth key."));
			}
		}
		return result;
	}

}

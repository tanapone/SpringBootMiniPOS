package com.spring.minipos.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class InvoiceDetail implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
	private Invoice invoice;
	
	@Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name="quantity")
	private int quantity;
	
	@Temporal(TemporalType.DATE)
	@Column(name="prodcutInDate",columnDefinition="DATETIME")
	private Date productInDate;

	@Column(name="product_in")
	private String productIn;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getProductInDate() {
		return productInDate;
	}

	public void setProductInDate(Date productInDate) {
		this.productInDate = productInDate;
	}

	public String getProductIn() {
		return productIn;
	}

	public void setProductIn(String productIn) {
		this.productIn = productIn;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}

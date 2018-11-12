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

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;

@Entity
public class InvoiceDetail implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id",nullable=false)
	private Invoice invoice;
	
	@Expose
	@Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id",nullable=false)
	private Product product;
	
	@Expose
	@Column(name="quantity",nullable=false,length = 20)
	private int quantity;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name="product_in_date",nullable=false)
	private Date productInDate;

	@Expose
	@Column(name="product_in_quantity",nullable=false)
	private int productInQuantity;
	
	@Expose
	@Column(name="product_capital_price",nullable=false)
	private double productCapitalPrice ;
	
	
	public InvoiceDetail() {}
	
	public InvoiceDetail(Invoice invoice, Product product, int quantity, Date productInDate, int productInQuantity,
			double productCapitalPrice) {
		this.invoice = invoice;
		this.product = product;
		this.quantity = quantity;
		this.productInDate = productInDate;
		this.productInQuantity = productInQuantity;
		this.productCapitalPrice = productCapitalPrice;
	}

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

	public int getProductInQuantity() {
		return productInQuantity;
	}

	public void setProductInQuantity(int productInQuantity) {
		this.productInQuantity = productInQuantity;
	}

	public double getProductCapitalPrice() {
		return productCapitalPrice;
	}

	public void setProductCapitalPrice(double productCapitalPrice) {
		this.productCapitalPrice = productCapitalPrice;
	}
	
	
	
}

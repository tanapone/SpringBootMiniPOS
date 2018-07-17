package com.spring.minipos.entity;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@AssociationOverrides({
	@AssociationOverride(name="invoiceDetailID.invoice",joinColumns = @JoinColumn(name="invoice_id"))
	,@AssociationOverride(name="invoiceDetailID.product",joinColumns = @JoinColumn(name="product_id"))
})
public class InvoiceDetail {
	
	@EmbeddedId
	private InvoiceDetailId invoiceDetailID = new InvoiceDetailId();
	
	@Column(name="quantity")
	private int quantity;
	
	@Temporal(TemporalType.DATE)
	@Column(name="prodcutInDate")
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

	public InvoiceDetailId getInvoiceDetailID() {
		return invoiceDetailID;
	}

	public void setInvoiceDetailID(InvoiceDetailId invoiceDetailID) {
		this.invoiceDetailID = invoiceDetailID;
	}

	public String getProductIn() {
		return productIn;
	}

	public void setProductIn(String productIn) {
		this.productIn = productIn;
	}
	
	
	
}

package com.spring.minipos.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="invoice")
public class Invoice {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="invoice_id",nullable=false,length = 20)
	private long id;
	
	@Expose
	@Temporal(TemporalType.DATE)
	@Column(name="invoiceDate",columnDefinition="DATETIME",nullable=false,length = 60)
	private Date invoiceDate = new Date();

	@Expose
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="company_id")
	private Company company;
	
	@Expose
	@OneToMany(mappedBy="invoice",cascade = CascadeType.ALL)
	private List<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>(); 
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public List<InvoiceDetail> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}

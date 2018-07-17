package com.spring.minipos.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="company_id")
	private long id;
	
	@Column(name="company_name",nullable=false)
	private String companyName;
	
	@Column(name="company_phone_number",nullable=false)
	private String companyPhoneNumber;
	
	@Column(name="company_email",nullable=false)
	private String companyEmail;
	
	@Column(name="company_address",nullable=false)
	private String companyAddress;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="company")
	private List<Product> products;
	
	public Company() {}
	
	public Company(String companyName, String companyPhoneNumber, String companyEmail, String companyAddress) {
		this.companyName = companyName;
		this.companyPhoneNumber = companyPhoneNumber;
		this.companyEmail = companyEmail;
		this.companyAddress = companyAddress;
	}
	
	public Company(String companyName, String companyPhoneNumber, String companyEmail, String companyAddress,
			List<Product> products) {
		super();
		this.companyName = companyName;
		this.companyPhoneNumber = companyPhoneNumber;
		this.companyEmail = companyEmail;
		this.companyAddress = companyAddress;
		this.products = products;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}

	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	
}

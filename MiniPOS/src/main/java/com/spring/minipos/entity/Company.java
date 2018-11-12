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
import javax.validation.constraints.NotBlank;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="company")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name="company_id",length = 20)
	private long id;
	
	@Expose
	@NotBlank
	@Column(name="company_name",nullable=false,unique=true,length = 120)
	private String companyName;
	
	@Expose
	@Column(name="company_phone_number",nullable=false,length = 12)
	private String companyPhoneNumber;
	
	@Expose
	@Column(name="company_email",nullable=false,length = 100)
	private String companyEmail;
	
	@Expose
	@Column(name="company_address",nullable=false,length = 255)
	private String companyAddress;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="company")
	private List<Product> products;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="company")
	private List<Invoice> invocies;
	
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

	public List<Invoice> getInvocies() {
		return invocies;
	}

	public void setInvocies(List<Invoice> invocies) {
		this.invocies = invocies;
	}
	
	
}

package com.spring.minipos.entity;

import java.util.ArrayList;
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

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name="product_id")
	private long id;
	
	@Expose
	@Column(name="product_name")
	private String productName;
	
	@Expose
	@Column(name="product_barcode_id")
	private String productBarcodeID;
	
	@Expose
	@Column(name="product_capital_price")
	private double productCapitalPrice;
	
	@Expose
	@Column(name="product_sale_price")
	private double productSalePrice;

	@Expose
	@Column(name="product_minimum")
	private int productMinimum;
	
	@Expose
	@Column(name="product_qty")
	private int productQty;
	
	@Expose
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="category_id")
	private Category category;
	
	@Expose
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="company_id")
	private Company company;

	@Expose
	@Column(name="product_statuts")
	@Type(type="true_false")
	private boolean productStatus;
	
	@OneToMany(mappedBy="product",cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	
	@OneToMany(mappedBy="invoiceDetailID.product",cascade = CascadeType.ALL)
	private List<InvoiceDetail> invoiceDetail = new ArrayList<InvoiceDetail>();
	
	public Product() {}
	
	public Product(String productName, String productBarcodeID, double productCapitalPrice, double productSalePrice,
			int productMinimum, int productQty) {
		this.productName = productName;
		this.productBarcodeID = productBarcodeID;
		this.productCapitalPrice = productCapitalPrice;
		this.productSalePrice = productSalePrice;
		this.productMinimum = productMinimum;
		this.productQty = productQty;
	}

	public Product(String productName, String productBarcodeID, double productCapitalPrice, double productSalePrice,
			int productMinimum, int productQty, Category category, Company company, List<OrderDetail> orderDetails
			,List<InvoiceDetail> invoiceDetail) {
		this.productName = productName;
		this.productBarcodeID = productBarcodeID;
		this.productCapitalPrice = productCapitalPrice;
		this.productSalePrice = productSalePrice;
		this.productMinimum = productMinimum;
		this.productQty = productQty;
		this.category = category;
		this.company = company;
		this.orderDetails = orderDetails;
		this.invoiceDetail = invoiceDetail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBarcodeID() {
		return productBarcodeID;
	}

	public void setProductBarcodeID(String productBarcodeID) {
		this.productBarcodeID = productBarcodeID;
	}

	public double getProductCapitalPrice() {
		return productCapitalPrice;
	}

	public void setProductCapitalPrice(double productCapitalPrice) {
		this.productCapitalPrice = productCapitalPrice;
	}

	public double getProductSalePrice() {
		return productSalePrice;
	}

	public void setProductSalePrice(double productSalePrice) {
		this.productSalePrice = productSalePrice;
	}

	public int getProductMinimum() {
		return productMinimum;
	}

	public void setProductMinimum(int productMinimum) {
		this.productMinimum = productMinimum;
	}

	public int getProductQty() {
		return productQty;
	}

	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<InvoiceDetail> getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(List<InvoiceDetail> invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	public boolean isProductStatus() {
		return productStatus;
	}

	public void setProductStatus(boolean productStatus) {
		this.productStatus = productStatus;
	}

	
	
	
}
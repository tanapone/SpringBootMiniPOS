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

import com.google.gson.annotations.Expose;

@Entity
@Table(name="category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	@Column(name="category_id")
	private long id;
	
	@Expose
	@Column(name="category_name",unique = true,nullable = false)
	private String categoryName;
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="category")
	private List<Product> products;

	public Category() {}
	
	public Category(String categoryName) {
		this.categoryName = categoryName;
	}

	public Category(String categoryName, List<Product> products) {
		this.categoryName = categoryName;
		this.products = products;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	
}

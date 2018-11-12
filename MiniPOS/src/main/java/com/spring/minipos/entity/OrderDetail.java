package com.spring.minipos.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;



@Entity
@Table(name="order_detail")
public class OrderDetail implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
	private Order order;
    
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id",nullable=false)
    @Expose
	private Product product;
    
    @Expose
    @Column(name = "product_amount",nullable=false)
    private int productAmount;
    
    @Expose
    @Column(name = "product_capital_price",nullable=false)
    private double productCaptialPrice;
    
    @Expose
    @Column(name = "product_sale_price",nullable=false)
    private double productSalePrice;
    
    
    public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	public double getProductCaptialPrice() {
		return productCaptialPrice;
	}

	public void setProductCaptialPrice(double productCaptialPrice) {
		this.productCaptialPrice = productCaptialPrice;
	}

	public double getProductSalePrice() {
		return productSalePrice;
	}

	public void setProductSalePrice(double productSalePrice) {
		this.productSalePrice = productSalePrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

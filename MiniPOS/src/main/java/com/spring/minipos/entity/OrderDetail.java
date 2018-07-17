package com.spring.minipos.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;


@Entity
@AssociationOverrides({
	@AssociationOverride(name="orderDetailID.order",joinColumns = @JoinColumn(name="order_id"))
	,@AssociationOverride(name="orderDetailID.product",joinColumns = @JoinColumn(name="product_id"))
})
public class OrderDetail {
	
	@EmbeddedId
	private OrderDetailId orderDetailID = new OrderDetailId();
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="price")
	private double price;

	public OrderDetailId getPk() {
		return orderDetailID;
	}

	public void setPk(OrderDetailId pk) {
		this.orderDetailID = pk;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}

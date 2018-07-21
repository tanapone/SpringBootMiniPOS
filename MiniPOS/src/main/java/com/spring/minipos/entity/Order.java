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
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	@Expose
	private long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="orderDate")
	@Expose
	private Date orderDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	@Expose
	private User user;

	@OneToMany(mappedBy="orderDetailID.order",cascade = CascadeType.ALL)
	@Expose
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	
	public Order() {}
	
	public Order(Date orderDate, User user) {
		super();
		this.orderDate = orderDate;
		this.user = user;
	}

	public Order(Date orderDate, User user, List<OrderDetail> orderDetails) {
		super();
		this.orderDate = orderDate;
		this.user = user;
		this.orderDetails = orderDetails;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	
}

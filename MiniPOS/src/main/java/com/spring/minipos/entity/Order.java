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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;


@Entity
@Table(name="orders")
public class Order {
	
	public Order() {	
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	@Expose
	private long id;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_date",columnDefinition="DATETIME")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd,HH:00", timezone="CER")
	@Expose
	private Date orderDate = new Date();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	@Expose
	private User user;
	
	@OneToMany(mappedBy="order",cascade = CascadeType.ALL)
	@Expose
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	
	@Expose
	@Column(name="order_sum_price")
	private double sumPrice;
	
	@Expose
	@Column(name="order_profit")
	private double profit;
	
	public Order(User user) {
		this.user = user;
	}
	
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

	public double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}
	
	
}

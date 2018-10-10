package com.spring.minipos.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.minipos.entity.Order;
import com.spring.minipos.repository.OrderRepository;

@Service
public class OrderServices {
	
	@Autowired
	OrderRepository orderRepository;
	
	public Order save(Order order) {
		return orderRepository.save(order);
	}
	
	public List<Order> findAll(){
		return orderRepository.findAll();
	}
	
	public Order getLast() {
		return orderRepository.findFirstByOrderByIdDesc();
	}
	
	public List<Order> getOrderBetweenDate(Date startDate,Date endDate){
		return orderRepository.findOrderBetweenDate(startDate, endDate);
	}
}

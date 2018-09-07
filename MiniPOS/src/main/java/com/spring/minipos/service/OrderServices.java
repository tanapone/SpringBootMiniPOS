package com.spring.minipos.service;

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
}

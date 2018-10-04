package com.spring.minipos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.minipos.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	Order findFirstByOrderByIdDesc();
	
}

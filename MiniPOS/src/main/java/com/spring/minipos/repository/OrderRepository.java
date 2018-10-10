package com.spring.minipos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.minipos.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	Order findFirstByOrderByIdDesc();
	
	@Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN ?1 AND ?2 GROUP BY o")
	List<Order> findOrderBetweenDate(Date startDate,Date endDate);
	
}

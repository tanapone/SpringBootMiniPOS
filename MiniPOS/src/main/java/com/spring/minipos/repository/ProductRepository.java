package com.spring.minipos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.minipos.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long>{
	
	@Query("SELECT p FROM Product p WHERE p.id = ?1")
	Product findProductById(long id);
	
	@Query("SELECT p FROM Product p WHERE p.productName = ?1")
	Product findProductByProductName(String productName);
	
	@Query("SELECT p FROM Product p WHERE p.productBarcodeID = ?1")
	Product findProductByProductBarcodeID(String productBarcodeID);
	
}

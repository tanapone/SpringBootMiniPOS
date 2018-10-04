package com.spring.minipos.repository;

import java.util.List;

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
	
	@Query("SELECT p FROM Product p WHERE p.productQty <= p.productMinimum")
	List<Product> findLessProducts();
	
	@Query("SELECT p FROM Product p WHERE p.productName LIKE %?1% AND p.productStatus != 'F' ")
	List<Product> findProductByNameMobile(String name);
	
	@Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND p.productStatus != 'F'")
	List<Product> findProductByCategoryMobile(long id);
	
	@Query("SELECT p FROM Product p WHERE p.productQty <= p.productMinimum AND p.productStatus != 'F'")
	List<Product> findLessProductsMobile();
}

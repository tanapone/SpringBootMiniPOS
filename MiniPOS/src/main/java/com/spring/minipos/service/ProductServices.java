package com.spring.minipos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.minipos.entity.Product;
import com.spring.minipos.repository.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	ProductRepository productRepository;
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public Product findProductById(long id) {
		return productRepository.findProductById(id);
	}
	
	public Product findProductByProductName(String productName) {
		return productRepository.findProductByProductName(productName);
	}
	
	public Product findProductByProductBarcodeID(String productBarcodeID) {
		return productRepository.findProductByProductBarcodeID(productBarcodeID);
	}
	
	public List<Product> findLessProducts() {
		return productRepository.findLessProducts();
	}
	
	public void delete(Product product) {
		productRepository.delete(product);
	}
	
}

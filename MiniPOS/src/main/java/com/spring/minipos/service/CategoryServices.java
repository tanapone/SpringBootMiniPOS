package com.spring.minipos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.minipos.entity.Category;
import com.spring.minipos.repository.CategoryRepository;

@Service
public class CategoryServices {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public Category save(Category category) {
		return categoryRepository.save(category);
	}
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Category findCategoryById(long id) {
		return categoryRepository.findCategoryById(id);
	}
	
	public Category findCategoryByName(String categoryName) {
		return categoryRepository.findCategoryByName(categoryName);
	}
	
	public void delete(Category category) {
		categoryRepository.delete(category);
	}
}

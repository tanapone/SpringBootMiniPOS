package com.spring.minipos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.minipos.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query("SELECT c FROM Category c WHERE c.id = ?1")
	Category findCategoryById(long id);
	
	@Query("SELECT c FROM Category c WHERE c.categoryName = ?1")
	Category findCategoryByName(String categoryName);
}

package com.spring.minipos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.minipos.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
	@Query("SELECT c FROM Company c WHERE c.companyName = ?1 ")
	Company findCompanyByName(String companyName);
	
	@Query("SELECT c FROM Company c WHERE c.id = ?1")
	Company findCompanyById(long id);
}

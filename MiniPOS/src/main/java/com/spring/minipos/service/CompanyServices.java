package com.spring.minipos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.minipos.entity.Company;
import com.spring.minipos.repository.CompanyRepository;

@Service
public class CompanyServices {
	
	@Autowired
	CompanyRepository companyRepository;
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}
	
}

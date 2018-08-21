package com.spring.minipos.service;

import java.util.List;

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
	
	public Company findCompanyByName(String companyName) {
		return companyRepository.findCompanyByName(companyName);
	}
	
	public Company findCompanyById(long id) {
		return companyRepository.findCompanyById(id);
	}
	
	public List<Company> findAll(){
		return companyRepository.findAll();
	}
	
	public void delete(Company company) {
		companyRepository.delete(company);
	}
	
}

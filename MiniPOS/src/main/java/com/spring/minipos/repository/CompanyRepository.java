package com.spring.minipos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.minipos.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

}

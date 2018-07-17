package com.spring.minipos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.spring.minipos.entity.Company;
import com.spring.minipos.entity.Message;
import com.spring.minipos.service.CompanyServices;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
public class CompanyController {

	@Autowired
	CompanyServices companyService;
	
	@Autowired
	UserServices userServices;
	
	@PostMapping("/create/company")
	public String createCompany(@Valid @RequestBody Company company,
			@RequestParam(value="tokenKey" ,required=false) String tokenKey) {
		String result = null;
		if(tokenKey == null) {
			result = new Gson().toJson(new Message("Required token key."));
		}else {
			if(userServices.checkTokenKey(tokenKey)!=null) {
				if(userServices.checkTokenKey(tokenKey).getUserType() == 1) {
					result = new Gson().toJson(companyService.save(company));
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong token key."));
			}
		}
		return result;
	}
	
}

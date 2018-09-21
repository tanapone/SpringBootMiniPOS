package com.spring.minipos.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spring.minipos.entity.Company;
import com.spring.minipos.entity.Message;
import com.spring.minipos.service.CompanyServices;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController {

	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	@Autowired
	CompanyServices companyService;
	
	@Autowired
	UserServices userServices;
	
	@PostMapping("/create/company")
	public String createCompany(@Valid @RequestBody Company company,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(companyService.findCompanyByName(company.getCompanyName())!=null) {
						result = new Gson().toJson(new Message("Please change company name."));
						}else {
							result = new Gson().toJson(companyService.save(company));
						}
					}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	
	@PostMapping("/update/company")
	public String updateCompany(@Valid @RequestBody Company company,
			@RequestParam(value = "authKey", required = false) String authKey) throws NoSuchAlgorithmException {
		String result = null;
		Company companyByUsername = companyService.findCompanyByName(company.getCompanyName());
		if (authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if (companyService.findCompanyById(company.getId()) != null) {
						if (companyByUsername!= null && companyByUsername.getId() != company.getId()) {
							result = gson.toJson(new Message("Please change company name."));
						} else {
							result = gson.toJson(companyService.save(company));
						}
					} else {
						result = gson.toJson(new Message("no company detail."));
					}
				} else {
					result = gson.toJson(new Message("No permission."));
				}
			} else {
				result = gson.toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	
	@GetMapping("/companies")
	public String showAllCompanies(@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(companyService.findAll());
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong auth key."));
			}
		}
		return result;
	}
	
	@DeleteMapping("/delete/company/{id}")
	public String removeCompany(@PathVariable long id
			,@RequestParam(value = "authKey", required = false) String authKey) {
		String result = null;
		if (authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		} else {
			if (userServices.checkAuthKey(authKey) != null) {
				if (userServices.checkAuthKey(authKey).getUserType() == 1) {
					if(companyService.findCompanyById(id)!=null) {
						if(companyService.findCompanyById(id).getProducts().size()>0) {
							result = gson.toJson(new Message("This company still have products."));
						}else {
							Company company = new Company();
							company = companyService.findCompanyById(id);
							companyService.delete(company);
							result = gson.toJson(new Message("Success."));
						}
					}else {
						result = gson.toJson(new Message("no company detail."));
					}
				} else {
					result = gson.toJson(new Message("No permission."));
				}
			} else {
				result = gson.toJson(new Message("Wrong auth key."));
			}
		}

		return result;
	}
	
}

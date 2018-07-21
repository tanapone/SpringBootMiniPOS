package com.spring.minipos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.spring.minipos.entity.Category;
import com.spring.minipos.entity.Message;
import com.spring.minipos.service.CategoryServices;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
public class CategoryController {
	
	@Autowired
	CategoryServices categoryServices;
	
	@Autowired
	UserServices userServices;
	
	@PostMapping("create/category")
	public String createCategory(@Valid @RequestBody Category category,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = new Gson().toJson(categoryServices.save(category));
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong auth key."));
			}
		}
		
		return result;
	}
	
}

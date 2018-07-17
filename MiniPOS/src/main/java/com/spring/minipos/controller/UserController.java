package com.spring.minipos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.spring.minipos.entity.Message;
import com.spring.minipos.entity.User;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
public class UserController {
	
	@Autowired
	UserServices userServices;
	
	@PostMapping("/create/user")
	public String createUserAcc(@Valid @RequestBody User user,
			@RequestParam(value="tokenKey" ,required=false) String tokenKey) {
		String result = null;
		if(tokenKey == null) {
			result = new Gson().toJson(new Message("Required token key."));
		}else {
			if(userServices.checkTokenKey(tokenKey)!=null) {
				if(userServices.checkTokenKey(tokenKey).getUserType() == 1) {
					result = new Gson().toJson(userServices.save(user));
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong token key."));
			}
		}
		
		return result;
	}
	
	@GetMapping("/users")
	public String showAllUsers(@RequestParam(value="tokenKey" ,required=false) String tokenKey){
		String result = null;
		if(tokenKey == null) {
			result = new Gson().toJson(new Message("Required token key."));
		}else {
			if(userServices.checkTokenKey(tokenKey)!=null) {
				if(userServices.checkTokenKey(tokenKey).getUserType() == 1) {
					result = new Gson().toJson(userServices.findAll());
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong token key."));
			}
		}
		
		return result;
	}
	
	@GetMapping("/user/id/{id}")
	public String findUserById(@PathVariable long id) {
		if (userServices.findUserById(id) != null) {
			return new Gson().toJson(userServices.findUserById(id));
		}else {
			return new Gson().toJson(new Message("User not found"));
		}
	}
	
	@GetMapping("/user/username/{username}")
	public String findUserByUsername(@PathVariable String username,
			@RequestParam(value="tokenKey" ,required=false) String tokenKey) {
		String result = null;
		
		if(tokenKey == null) {
			result = new Gson().toJson(new Message("Required token key."));
		}else {
			if(userServices.checkTokenKey(tokenKey)!=null) {
				if(userServices.checkTokenKey(tokenKey).getUserType() == 1) {
					result = new Gson().toJson(userServices.findUserByUsername(username));
				}else {
					result = new Gson().toJson(new Message("No permission."));
				}
			}else {
				result = new Gson().toJson(new Message("Wrong token key."));
			}
		}
		return result;
		
	}
	
	@PostMapping("/login")
	public String checkLogin(@RequestBody User user) {
		if(userServices.checkLogin(user.getUsername(), user.getPassword())!= null) {
			return new Gson().toJson(userServices.checkLogin(user.getUsername(), user.getPassword()));
		}else {
			return new Gson().toJson(new Message("Wrong username or password"));
		}
	}
	
	@GetMapping("/create/user/admin")
	public String createAdminUser() {
		if(userServices.findAll().size()<1) {
			User user = new User();
			user.setUsername("admin");
			user.setPassword("123456");
			user.setFirstName("Tanapone");
			user.setLastName("Kanongsri");
			user.setPhoneNumber("0931385440");
			user.setUserType(1);
			user.setEmail("tanapone58110@gmail.com");
			user.setAddress("298/23");
			return new Gson().toJson(userServices.save(user));
		}else {
			return new Gson().toJson(new Message("Admin account already set."));
		}
		
	}
	
}

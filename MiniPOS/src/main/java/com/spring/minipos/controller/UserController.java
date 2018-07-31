package com.spring.minipos.controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

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
import com.google.gson.GsonBuilder;
import com.spring.minipos.entity.MD5;
import com.spring.minipos.entity.Message;
import com.spring.minipos.entity.User;
import com.spring.minipos.service.UserServices;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins="*",allowedHeaders="*")
public class UserController {
	
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	@Autowired
	UserServices userServices;
	
	@PostMapping("/create/user")
	public String createUserAcc(@Valid @RequestBody User user,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(userServices.save(user));
				}else {
					result = gson.toJson(new Message("No permission."));
				}
			}else {
				result = gson.toJson(new Message("Wrong auth key."));
			}
		}
		
		return result;
	}
	
	@GetMapping("/users")
	public String showAllUsers(@RequestParam(value="authKey" ,required=false) String authKey){
		String result = null;
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(userServices.findAll());
				}else {
					result = gson.toJson(new Message("No permission."));
				}
			}else {
				result = gson.toJson(new Message("Wrong auth key."));
			}
		}
		
		return result;
	}
	
	@GetMapping("/user/id/{id}")
	public String findUserById(@PathVariable long id) {
		if (userServices.findUserById(id) != null) {
			return gson.toJson(userServices.findUserById(id));
		}else {
			return gson.toJson(new Message("User not found"));
		}
	}
	
	@GetMapping("/user/username/{username}")
	public String findUserByUsername(@PathVariable String username,
			@RequestParam(value="authKey" ,required=false) String authKey) {
		String result = null;
		
		if(authKey == null) {
			result = new Gson().toJson(new Message("Required auth key."));
		}else {
			if(userServices.checkAuthKey(authKey)!=null) {
				if(userServices.checkAuthKey(authKey).getUserType() == 1) {
					result = gson.toJson(userServices.findUserByUsername(username));
				}else {
					result = gson.toJson(new Message("No permission."));
				}
			}else {
				result = gson.toJson(new Message("Wrong auth key."));
			}
		}
		return result;
		
	}
	
	@PostMapping("/login")
	public String checkLogin(@RequestBody User user) throws NoSuchAlgorithmException {
		if(userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding())!= null) {
			return gson.toJson(userServices.checkLogin(user.getUsername(), new MD5(user.getPassword()).Encoding()));
		}else {
			return gson.toJson(new Message("Wrong username or password"));
		}
	}
	
	@GetMapping("/user")
	public String getUserByAuthKey(@RequestParam(value="authKey" ,required=false)String authKey) {
		String result = null;	
		if(authKey == null) {
				result = new Gson().toJson(new Message("Required auth key."));
			}else {
				if(userServices.checkAuthKey(authKey)!=null) {
					result = gson.toJson(userServices.checkAuthKey(authKey));
				}else {
					result = gson.toJson(new Message("Wrong auth key."));
				}
		}
		return result;
	}
	
	@GetMapping("/create/user/admin")
	public String createAdminUser() throws ParseException, NoSuchAlgorithmException {
		if(userServices.findAll().size()<1) {
			User user = new User();
			user.setUsername("admin");
			user.setPassword(new MD5("123456").Encoding());
			user.setFirstName("Tanapone");
			user.setLastName("Kanongsri");
			user.setPhoneNumber("0931385440");
			user.setUserType(1);
			user.setEmail("tanapone58110@gmail.com");
			user.setAddress("298/23");
			return gson.toJson(userServices.save(user));
		}else {
			return gson.toJson(new Message("Admin account already set."));
		}
		
	}
	
}

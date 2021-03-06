package com.spring.minipos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.minipos.entity.User;
import com.spring.minipos.repository.UserRepository;

@Service
public class UserServices {
	
	@Autowired
	UserRepository userRepository;
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User checkLogin(String username,String password) {
		return userRepository.checkLogin(username, password);
	}

	public User findUserByUsername(String username) {
		return userRepository.findUserByUser(username);
	}
	
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	public User checkAuthKey(String authKey) {
		return userRepository.checkAuthKey(authKey);
	}
	
//	public User getLastUser() {
//		return userRepository.findFirstByOrderByIdDesc();
//	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
	
}

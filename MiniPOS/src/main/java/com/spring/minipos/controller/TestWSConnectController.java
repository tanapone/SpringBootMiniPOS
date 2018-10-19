package com.spring.minipos.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.spring.minipos.entity.MessageModel;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestWSConnectController {
	
	@GetMapping("testConnector")
	public String testConnector() {
		String result = "";
		result = new Gson().toJson(new MessageModel("Connected"));
		return result;
	}
}

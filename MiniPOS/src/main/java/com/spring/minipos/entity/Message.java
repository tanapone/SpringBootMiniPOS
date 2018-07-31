package com.spring.minipos.entity;

import com.google.gson.annotations.Expose;

public class Message {
	@Expose
	private String message;
	
	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

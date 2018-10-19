package com.spring.minipos.entity;

import com.google.gson.annotations.Expose;

public class MessageModel {
	@Expose
	private String message;
	
	public MessageModel(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

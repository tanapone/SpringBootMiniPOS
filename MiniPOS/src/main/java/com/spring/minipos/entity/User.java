package com.spring.minipos.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
//@NamedQuery(name = "User.findUserById",
//query = "SELECT u FROM User u WHERE u.id = ?1")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private long id;
	
	@Column(name="username",unique=true,nullable=false)
	private String username;
	
	@Column(name="password",nullable=false)
	private String password;
	
	@Column(name="user_type",nullable=false)
	private int userType;
	
	@Column(name="user_firstname",nullable=false)
	private String firstName;
	
	@Column(name="user_lastname",nullable=false)
	private String lastName;
	
	@Column(name="user_email",nullable=false)
	private String email;
	
	@Column(name="user_phone_number",nullable=false)
	private String phoneNumber;
	
	@Column(name="user_address",nullable=false)
	private String address;
	
	@Column(name="user_token_key",unique = true,nullable=false)
	private String tokenKey;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	private List<Order> orders = new ArrayList<Order>();
	
	public User() {
		tokenKey = UUID.randomUUID().toString();
	}
	
	public User(String username, String password, int userType, String firstName, String lastName, String email,
			String phoneNumber, String address,String tokenKey) {
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.tokenKey = tokenKey;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	
	
	
	
}

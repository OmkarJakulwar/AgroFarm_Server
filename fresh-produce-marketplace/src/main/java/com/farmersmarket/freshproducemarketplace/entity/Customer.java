package com.farmersmarket.freshproducemarketplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="AF_CUSTOMER")
public class Customer {

	@Id
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PASSWORD")
	private String password;	
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name="ADDRESS")
	private String address;	
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [" + ", name=" + name + ", address=" + address + ", emailId=" + emailId
				+ ", phoneNumber=" + phoneNumber + ", password=" + password + "]";
	}
	
}

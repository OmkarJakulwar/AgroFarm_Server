package com.farmersmarket.freshproducemarketplace.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerDTO {

	@NotNull(message="should not be null")
	@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+")
    private String emailId;
	@Pattern(regexp = "([A-Za-z])+(\\s[A-Za-z]+)*")
    private String name;
	@NotNull(message = "Password is not valid")
	@Pattern(regexp = ".*[A-Z]+.*",message = "invalid format UpperCase")
	@Pattern(regexp = ".*[a-z]+.*",message = "invalid format LowerCase")
	@Pattern(regexp = ".*[0-9]+.*",message = "invalid format Number")
	@Pattern(regexp = ".*[^a-zA-Z-0-9].*",message = "invalid format SpecialCharacter")
    private String password;
	@Size(max = 10, min = 10, message = "Invalid Phone Number")
	@Pattern(regexp = "[0-9]+", message = "Invalid Phone Number")
    private String phoneNumber;
	@NotNull(message = "Address absent")
    private String address;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

   
}

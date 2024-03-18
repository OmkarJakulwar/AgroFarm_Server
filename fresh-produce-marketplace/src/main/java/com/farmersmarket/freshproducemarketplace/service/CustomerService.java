package com.farmersmarket.freshproducemarketplace.service;


import com.farmersmarket.freshproducemarketplace.dto.CustomerDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;

public interface CustomerService {
	
	CustomerDTO authenticateCustomer(String emailId, String password) throws AgroFarmException;
	
	String registerNewCustomer(CustomerDTO customerDTO) throws AgroFarmException;
	
	void updateShipingAddress(String customerEmailId, String address) throws AgroFarmException;
	
	void deleteShippingAddress(String customerEmailId) throws AgroFarmException;
	
    CustomerDTO getExistingCustomer(String emailId) throws AgroFarmException;
}

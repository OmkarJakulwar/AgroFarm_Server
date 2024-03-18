package com.farmersmarket.freshproducemarketplace.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmersmarket.freshproducemarketplace.dto.CustomerDTO;
import com.farmersmarket.freshproducemarketplace.entity.Customer;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.repository.CustomerRepository;



@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	public CustomerRepository customerRepository;
	
	@Override
	public String registerNewCustomer(CustomerDTO customerDTO) throws AgroFarmException {
		// TODO Auto-generated method stub
		String registerWithEmailId = null;
		boolean isEmailNotAvailable = customerRepository.findById(customerDTO.getEmailId().toLowerCase()).isEmpty();
		
		if(isEmailNotAvailable) {
			Customer cust1 = new Customer();
			cust1.setEmailId(customerDTO.getEmailId());
			cust1.setName(customerDTO.getName());
			cust1.setPassword(customerDTO.getPassword());
			cust1.setPhoneNumber(customerDTO.getPhoneNumber());
			cust1.setAddress(customerDTO.getAddress());
			
			customerRepository.save(cust1);
			registerWithEmailId = cust1.getEmailId();
		}else {
			throw new AgroFarmException("CustomerService.CUSTOMER_NOT_FOUND");
		}
			
		return registerWithEmailId;
		
	}
	
	@Override
	public CustomerDTO getExistingCustomer(String customerEmailId) throws AgroFarmException {
		// TODO Auto-generated method stub
		 // Find the customer by email id
        Optional<Customer> existingCustomer = customerRepository.findById(customerEmailId.toLowerCase());

        // If the customer is not found, throw an exception
        Customer customer = existingCustomer.orElseThrow(() ->
                new AgroFarmException("Customer not found with email id: " + customerEmailId));

		
		if(customer == null) {
			throw new AgroFarmException("CustomerService.CUSTOMER_NOT_FOUND");
		}
			
			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setName(customer.getName());
			customerDTO.setPassword(customer.getPassword());
			customerDTO.setAddress(customer.getAddress());
			customerDTO.setEmailId(customer.getEmailId());
			customerDTO.setPhoneNumber(customer.getPhoneNumber());
			
			return customerDTO;
	
	}

	@Override
	public CustomerDTO authenticateCustomer(String emailId, String password) throws AgroFarmException {
		CustomerDTO customerDTO = null;
		
		Optional<Customer> optionalCustomer = customerRepository.findById(emailId.toLowerCase());
		Customer customer = optionalCustomer.orElseThrow(()-> new AgroFarmException("CustomerService.CUSTOMER_NOT_FOUND"));
		if(!password.equals(customer.getPassword()))
			throw new AgroFarmException("CustomerService.INVALID_CREDENTIALS");
		
		customerDTO = new CustomerDTO();
		customerDTO.setEmailId(customer.getEmailId());
		customerDTO.setName(customer.getName());
		customerDTO.setPhoneNumber(customer.getPhoneNumber());
		customerDTO.setPassword(customer.getPassword());
		customerDTO.setAddress(customer.getAddress());
		return customerDTO;				
	}


	@Override
	public void updateShipingAddress(String customerEmailId, String address) throws AgroFarmException {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerEmailId.toLowerCase());
		Customer customer = optionalCustomer.orElseThrow(()-> new AgroFarmException("CustomerService.CUSTOMER_NOT_FOUND"));
		customer.setAddress(address);	
	}

	@Override
	public void deleteShippingAddress(String customerEmailId) throws AgroFarmException {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerEmailId.toLowerCase());
		Customer customer = optionalCustomer.orElseThrow(()-> new AgroFarmException("CustomerService.CUSTOMER_NOT_FOUND"));
		customer.setAddress(null);
	}	 
}

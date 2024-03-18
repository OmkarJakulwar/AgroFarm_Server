package com.farmersmarket.freshproducemarketplace.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmersmarket.freshproducemarketplace.dto.CustomerCredDTO;
import com.farmersmarket.freshproducemarketplace.dto.CustomerDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.service.CustomerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;



@RestController
@Validated
@RequestMapping("/customer-api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private Environment environment;
	
	static Log Logger = LogFactory.getLog(CustomerController.class);
	
	@PostMapping("/login")
	public ResponseEntity<CustomerDTO> authenticateCustomer(@Valid @RequestBody CustomerCredDTO customerCredDTO) throws AgroFarmException{
		
		Logger.info("CUSTOMER TRYING TO LOGIN, VALIDATING CREDENTIALS. CUSTOMER EMAIL ID: " + customerCredDTO.getEmailId());
		CustomerDTO customerDTOFromDB = customerService.authenticateCustomer(customerCredDTO.getEmailId(),customerCredDTO.getPassword());
		
		Logger.info("CUSTOMER LOGIN SUCCESS, CUSTOMER EMAIL: " + customerCredDTO.getEmailId());
		return new ResponseEntity<>(customerDTOFromDB, HttpStatus.OK); 
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerNewCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {
		
		System.out.println("Customer Trying to Register with Customer EmailId: " + customerDTO.getEmailId());
		String registerWithEmailId = customerService.registerNewCustomer(customerDTO);
		registerWithEmailId = environment.getProperty("CustomerAPI.CUSTOMER_REGISTRATION_SUCCESS") + registerWithEmailId ;
		
		return new ResponseEntity<>(registerWithEmailId,HttpStatus.OK);	
	}
	
	@PutMapping("/customer/{customerEmailId:.+}/address/")
	public ResponseEntity<String> updatingShippingAddress(
			@PathVariable @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", message = "{invalid.email.format}")	String customerEmailId, 
			@RequestBody String address) throws AgroFarmException {
		
		customerService.updateShipingAddress(customerEmailId, address);
		String modificationSuccessMsg = environment.getProperty("CustomerAPI.UPDATE_ADDRESS_SUCCESS");
		return new ResponseEntity<>(modificationSuccessMsg,HttpStatus.OK);		
	}
	
	@DeleteMapping(value = "/customer/{customerEmailId:.+}")
	public ResponseEntity<String> deleteShippingAddress(
			@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", message = "{invalid.email.format}") @PathVariable("customerEmailId") String customerEmailId) throws AgroFarmException{
		
		customerService.deleteShippingAddress(customerEmailId);
		String modificationSuccessMsg = environment.getProperty("CustomerAPI.CUSTOMER_ADDRESS_DELETED_SUCCESS");
		return new ResponseEntity<>(modificationSuccessMsg,HttpStatus.OK);
	}
	
	@GetMapping(value = "/customer/{customerEmailId}")
	public ResponseEntity<CustomerDTO> getExistingCustomer(@PathVariable("customerEmailId") String customerEmailId) throws AgroFarmException{
		
		CustomerDTO customerDTO = customerService.getExistingCustomer(customerEmailId);
		return new ResponseEntity<>(customerDTO,HttpStatus.OK);
	}
	
}

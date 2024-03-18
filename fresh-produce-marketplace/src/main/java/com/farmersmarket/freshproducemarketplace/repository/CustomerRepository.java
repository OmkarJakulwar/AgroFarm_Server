package com.farmersmarket.freshproducemarketplace.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farmersmarket.freshproducemarketplace.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>{

	
	List<Customer> findByPhoneNumber(String phoneNumber);
	

}

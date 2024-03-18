package com.farmersmarket.freshproducemarketplace.repository;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;

import com.farmersmarket.freshproducemarketplace.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
//	public List<Product> getAllProducts();
}

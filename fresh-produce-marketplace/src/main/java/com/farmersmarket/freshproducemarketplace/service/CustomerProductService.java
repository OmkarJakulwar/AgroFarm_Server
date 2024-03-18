package com.farmersmarket.freshproducemarketplace.service;

import java.util.List;

import com.farmersmarket.freshproducemarketplace.dto.ProductDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;

public interface CustomerProductService {
	
	List<ProductDTO> getAllProducts() throws AgroFarmException;
	
	ProductDTO getProductById(Integer productId) throws AgroFarmException;
	
	void reduceAvailableQuantity(Integer productId, Integer quantity) throws AgroFarmException;
	
}

package com.farmersmarket.freshproducemarketplace.service;

import java.util.Set;

import com.farmersmarket.freshproducemarketplace.dto.CartProductDTO;
import com.farmersmarket.freshproducemarketplace.dto.CustomerCartDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;

public interface CustomerCartService {
	
	Integer addProductToCart(CustomerCartDTO customerCart) throws AgroFarmException;
	
	Set<CartProductDTO> getProductsFromCart(String customerEmailId) throws AgroFarmException;
	
	void modifyQuantityOfProductInCart(String customerEmailId, Integer productId, Integer quantity) throws AgroFarmException;
	
	void deleteProductFromCart(String customerEmailId, Integer ProductId) throws AgroFarmException;
	
	void deleteAllProductsFromCart(String customerEmailId) throws AgroFarmException;

}

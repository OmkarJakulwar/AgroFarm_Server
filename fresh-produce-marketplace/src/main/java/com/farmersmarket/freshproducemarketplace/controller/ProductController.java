package com.farmersmarket.freshproducemarketplace.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmersmarket.freshproducemarketplace.dto.ProductDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.service.CustomerProductService;

@RestController
@Validated
@RequestMapping("/product-api")
public class ProductController {
	
	@Autowired
	private CustomerProductService customerProductService;
	
	@Autowired
	private Environment environment;
	
	Log  logger = LogFactory.getLog(ProductController.class);
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getProductById() throws AgroFarmException{
		
		logger.info("Recieved a request to get all products: ");
		List<ProductDTO> products = customerProductService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);

	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<ProductDTO> getProductByProductId(@PathVariable Integer productId) throws AgroFarmException {
		
		logger.info("Request for Product With Product Id: " + productId);
		ProductDTO productDTO = customerProductService.getProductById(productId);
		return new ResponseEntity<>(productDTO,HttpStatus.OK);
		
	}
	
	@PutMapping("/updatequantity/{productId}")
	public ResponseEntity<String> reduceAvailableQuantity(@PathVariable Integer productId, @RequestBody String quantity) throws AgroFarmException{
		
		logger.info("Recived a request to update the available quantity for product with productID: " + productId);
	    customerProductService.reduceAvailableQuantity(productId, Integer.parseInt(quantity));
	    return new ResponseEntity<>(environment.getProperty("{ProductAPI_REDUCE_QUANTITY_SUCCESSFUL}"),HttpStatus.OK);
	    
	}

}

package com.farmersmarket.freshproducemarketplace.controller;

import java.util.Set;

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
import org.springframework.web.client.RestTemplate;

import com.farmersmarket.freshproducemarketplace.dto.CartProductDTO;
import com.farmersmarket.freshproducemarketplace.dto.CustomerCartDTO;
import com.farmersmarket.freshproducemarketplace.dto.ProductDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.service.CustomerCartService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@Validated
@RequestMapping("/cart-api")
public class CartController {
	
	@Autowired
	private CustomerCartService customerCartService;
	
	@Autowired
    private Environment environment;
	
	@Autowired
	private RestTemplate template;
	
	static Log Logger = LogFactory.getLog(CustomerController.class);
	
	@PostMapping("/addProductsToCart")
	public ResponseEntity<String> addProductToCart(@Valid @RequestBody CustomerCartDTO customerCartDTO) throws AgroFarmException{
		
		Logger.info("Customer Trying to add products to cart with Customer EmailId  : " + 
		             customerCartDTO.getCustomerEmailId());	
		
        Integer cartId = customerCartService.addProductToCart(customerCartDTO);
        String msg = environment.getProperty("CustomerCartAPI.PRODUCT_ADDED_TO_CART") + ":" + cartId;
        Logger.info("Product added successfully to " + "CartId: " + cartId);
        
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}
	
	 @GetMapping("/customer/{customerEmailId}/products")
	    public ResponseEntity<Set<CartProductDTO>> getProductsFromCart(
	    		@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", 
	    		message = "{invalid.customeremail.format}") @PathVariable("customerEmailId") 
	    		String customerEmailId) throws AgroFarmException {
	        
		 Logger.info("Recived a request to get products details from : " + customerEmailId + " ");
		 
		 Set<CartProductDTO> cartProductDTOs = customerCartService.getProductsFromCart(customerEmailId);
		 for(CartProductDTO cartProductDTO : cartProductDTOs) {
			 Logger.info("Product Calls");
			 ProductDTO productDTO = template.getForEntity(
					 "http://localhost:8080/AgroFarm/product-api/product/" + cartProductDTO.getProduct().getProductId(), ProductDTO.class).getBody();
			 cartProductDTO.setProduct(productDTO);
			 Logger.info("Product Complete");			 
		 }
		 
		 return new ResponseEntity<Set<CartProductDTO>>(cartProductDTOs,HttpStatus.OK);
		 
	    }
	 
	 @DeleteMapping("/customer/{customerEmailId}/product/{productId}")
	    public ResponseEntity<String> deleteProductFromCart(
	    		@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-z]{2,}\\.[a-zA-Z][a-zA-Z.]+",message = "{invalid.customeremail.format}") @PathVariable("customerEmailId") String customerEmailId,
	    		@PathVariable("productId") Integer productId) throws AgroFarmException {
	        
		    Logger.info("Received a request to delete product with ID " + productId + " from cart for customer: " + customerEmailId);
	        // Call the service to delete the product from the cart
	        customerCartService.deleteProductFromCart(customerEmailId, productId);
	        String msg = environment.getProperty("CustomerCartAPI.PRODUCT_DELETED_FROM_CART_SUCCESS");
	        // Return a successful response with status code 204 (No Content)
	        return new ResponseEntity<String>(msg,HttpStatus.OK);
	    }
	
	
	  @PutMapping("/customer/{customerEmailId}/product/{productId}")
	    public ResponseEntity<String> modifyQuantityOfProductInCart(
	    		@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-z]{2,}\\.[a-zA-Z][a-zA-Z.]+",message = "{invalid.customeremail.format}") @PathVariable("customerEmailId") String customerEmailId,
	            @PathVariable("productId") Integer productId,
	            @RequestBody String quantity) throws AgroFarmException {
		  Logger.info("Recieved a request to modify the quantity of :" + productId + " product from  " + customerEmailId + " cart");
	        
	            customerCartService.modifyQuantityOfProductInCart(customerEmailId, productId,  Integer.parseInt(quantity));
	            String msg = environment.getProperty("CustomerCartAPI.PRODUCT_QUANTITY_UPDATE_FROM_CART_SUCCESS");
	            return new ResponseEntity<>(msg, HttpStatus.OK);
	        
	    }
	  
	  @DeleteMapping("/customer/{customerEmailId}/products")
	    public ResponseEntity<String> deleteAllProductsFromCart(
	    		@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-z]{2,}\\.[a-zA-Z][a-zA-Z.]+",message = "{invalid.customeremail.format}") @PathVariable("customerEmailId") String customerEmailId) throws AgroFarmException{
	      
		        Logger.info("Recived a request to clear " + customerEmailId + " cart");
		  
	            customerCartService.deleteAllProductsFromCart(customerEmailId);
	            String msg = environment.getProperty("CustomerCartAPI.ALL_PRODUCTS_DELETED");
	            return new ResponseEntity<>(msg, HttpStatus.OK);
	       
	    }
	  
}

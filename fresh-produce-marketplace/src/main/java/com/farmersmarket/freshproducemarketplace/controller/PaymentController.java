package com.farmersmarket.freshproducemarketplace.controller;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
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

import com.farmersmarket.freshproducemarketplace.dto.CardDTO;
import com.farmersmarket.freshproducemarketplace.dto.OrderDTO;
import com.farmersmarket.freshproducemarketplace.dto.OrderedProductDTO;
import com.farmersmarket.freshproducemarketplace.dto.TransactionDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.service.PaymentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@Validated
@RequestMapping("/payment-api")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private RestTemplate restTemplate;
	
	Log Logger = LogFactory.getLog(PaymentController.class);
	
	@PostMapping("/customer/{customerEmailId:.+}/cards")
	public ResponseEntity<String> addNewCard(@RequestBody CardDTO cardDTO, 
			@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", 
    		message = "{invalid.customeremail.format}") @PathVariable("customerEmailId")  String customerEmailId) 
    				throws AgroFarmException, NoSuchAlgorithmException{
		
		Logger.info("Recived request add new card for customer: " + customerEmailId);
		cardDTO.setCustomerEmailId(customerEmailId);
		
		int cardId;
		cardId = paymentService.addCustomerCard(customerEmailId, cardDTO);
		String msg = environment.getProperty("PaymentAPI.NEW_CARD_ADDED_SUCCESS");
		String toReturn = msg + cardId;
		toReturn = toReturn.trim();
		return new ResponseEntity<String>(toReturn,HttpStatus.OK);
	}
	
	@PutMapping("/update/card")
	public ResponseEntity<String> updateCustomerCard(@Valid @RequestBody CardDTO cardDTO) throws AgroFarmException, NoSuchAlgorithmException{
		
		Logger.info("Recieved request to update card: " + cardDTO.getCardId() + " of Customer : " + cardDTO.getCustomerEmailId());
		
		paymentService.updateCustomerCard(cardDTO);
		String modificationmsg = environment.getProperty("PaymentAPI.UPDATE_CARD_SUCCESS");
		
		return new ResponseEntity<String>(modificationmsg,HttpStatus.OK);	
	}
	
	@DeleteMapping("/customer/{customerEmailId:.+}/card/{cardId}/delete")
	public ResponseEntity<String> deleteCustomerCard(@PathVariable("cardId") Integer cardId, 
			@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", 
    		message = "{invalid.customeremail.format}") @PathVariable("customerEmailId") String customerEmailId) 
    				throws AgroFarmException{
		
		Logger.info("Recived request to delete card: " + cardId + "of customer: " + customerEmailId);
		
		paymentService.deleteCustomerCard(customerEmailId, cardId);
		String modifiedmsg = environment.getProperty("PaymentAPI.CUSTOMER_CARD_DELETED_SUCCESS");
				
		return new ResponseEntity<String>(modifiedmsg,HttpStatus.OK);
	}
	
	@GetMapping("/customer/{customerEmailId}/card-type/{cardType}")
	public ResponseEntity<List<CardDTO>> getCardsOfCustomer(@PathVariable("customerEmailId") String customerEmailId, @PathVariable("cardType") String cardType) 
			throws AgroFarmException{
		
		Logger.info("Recived request to fetch cards of customer: " + customerEmailId + " having card type as: " + cardType);
		
		 List<CardDTO> cards = paymentService.getCardsOfCustomer(customerEmailId, cardType);
         return new ResponseEntity<>(cards, HttpStatus.OK);
	}
	
	@GetMapping("/getCard/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable Integer cardId) throws AgroFarmException, NoSuchAlgorithmException {
        
            CardDTO cardDTO = paymentService.getCard(cardId);
            return new ResponseEntity<>(cardDTO, HttpStatus.OK);
    }
	
	@PostMapping("/customer/{customerEmailId}/order/{orderId}")
	public ResponseEntity<String> payForOrder(@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", 
    		message = "{invalid.customeremail.format}") @PathVariable("customerEmailId") String customerEmailId, 
			@NotNull(message = "{orderId.absent}") @PathVariable("orderId") Integer orderId, 
			@Valid @RequestBody CardDTO cardDTO) throws AgroFarmException, NoSuchAlgorithmException{
	
		 String successMessage = null;
	
		    // Your existing code to make the request

		 ResponseEntity<List<OrderDTO>> orderDetailsResponse = restTemplate.exchange(
			        "http://localhost:8080/AgroFarm/order-api/customer/" + customerEmailId + "/order/" + orderId,
			        HttpMethod.GET,
			        null,
			        new ParameterizedTypeReference<List<OrderDTO>>() {});

			List<OrderDTO> orderDTOList = orderDetailsResponse.getBody();
			
			if (orderDTOList != null && !orderDTOList.isEmpty()) {
			    // Process the list of OrderDTOs
			    for (OrderDTO orderDTO : orderDTOList) {
			        // Continue with the existing logic
			        TransactionDTO transactionDTO = new TransactionDTO();
			        transactionDTO.setCard(cardDTO);
			        transactionDTO.setOrder(orderDTO);
			        transactionDTO.setTotalPrice(orderDTO.getTotalPrice());
			        transactionDTO.setTransactionDate(LocalDateTime.now());

			        transactionDTO = paymentService.authenticatePayment(customerEmailId, transactionDTO);
			        Integer transactionId = paymentService.addTransaction(transactionDTO);

			        String transactionStatus = transactionDTO.getTransactionStatus().toString();
			        restTemplate.put("http://localhost:8080/AgroFarm/order-api/order/" + orderId + "/update/order-status",
			                transactionStatus);
			        
			     // Update product quantities
	                for (OrderedProductDTO orderedProductDTO : orderDTO.getOrderProducts()) {
	                	
	                	restTemplate.put("http://localhost:8080/AgroFarm/product-api/updatequantity/"
	    						+ orderedProductDTO.getProduct().getProductId(), orderedProductDTO.getQuantity());
	                    
	                }

			        successMessage = environment.getProperty("PaymentAPI.TRANSACTION_SUCCESSFULL_ONE") +
			                " " + cardDTO.getCardId() +
			                " " + environment.getProperty("PaymentAPI.TRANSACTION_SUCCESSFULL_TWO") +
			                " " + orderId +
			                " " + environment.getProperty("PaymentAPI.TRANSACTION_SUCCESSFULL_THREE") +
			                " " + transactionId;
			       
			    }
			} 
			
			 return new ResponseEntity<>(successMessage, HttpStatus.OK);
	
}
}


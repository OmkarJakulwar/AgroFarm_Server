package com.farmersmarket.freshproducemarketplace.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.farmersmarket.freshproducemarketplace.dto.CardDTO;
import com.farmersmarket.freshproducemarketplace.dto.TransactionDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;

public interface PaymentService {
	
	Integer addCustomerCard(String customerEmailId, CardDTO cardDTO) throws AgroFarmException, NoSuchAlgorithmException;
	
	void updateCustomerCard(CardDTO cardDTO) throws AgroFarmException, NoSuchAlgorithmException;
	
	void deleteCustomerCard(String customerEmailId, Integer cardId) throws AgroFarmException;
	
	CardDTO getCard(Integer cardId) throws AgroFarmException, NoSuchAlgorithmException;
	
	List<CardDTO> getCustomerCardOfCardType(String customerEmailId,String cardType) throws AgroFarmException;
	
	Integer addTransaction(TransactionDTO transactionDTO) throws AgroFarmException;
	
	TransactionDTO authenticatePayment(String customerEmailId, TransactionDTO transactionDTO) throws AgroFarmException, NoSuchAlgorithmException;
	
	List<CardDTO> getCardsOfCustomer (String customerEmailId, String cardType) throws AgroFarmException;
	
}

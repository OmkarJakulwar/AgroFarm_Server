package com.farmersmarket.freshproducemarketplace.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmersmarket.freshproducemarketplace.dto.CardDTO;
import com.farmersmarket.freshproducemarketplace.dto.TransactionDTO;
import com.farmersmarket.freshproducemarketplace.dto.TransactionStatus;
import com.farmersmarket.freshproducemarketplace.entity.Card;
import com.farmersmarket.freshproducemarketplace.entity.Transaction;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.repository.CardRepository;
import com.farmersmarket.freshproducemarketplace.repository.TransactionRepository;
import com.farmersmarket.freshproducemarketplace.utility.HashingUtility;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Integer addCustomerCard(String customerEmailId, CardDTO cardDTO)
			throws AgroFarmException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		List<Card> listOfCustomerCards = cardRepository.findByCustomerEmailId(customerEmailId);
		
		if (listOfCustomerCards.isEmpty())
			throw new AgroFarmException("PaymentService.CUSTOMER_NOT_FOUND");
		
		cardDTO.setHashCvv(HashingUtility.getHashValue(cardDTO.getCvv().toString()));
		
		Card newCard = new Card();
		newCard.setNameOnCard(cardDTO.getNameOnCard());
		newCard.setCardNumber(cardDTO.getCardNumber());
		newCard.setCardType(cardDTO.getCardType());
		newCard.setExpiryDate(cardDTO.getExpiryDate());
		newCard.setCvv(cardDTO.getHashCvv());
		newCard.setCustomerEmailId(cardDTO.getCustomerEmailId());

		cardRepository.save(newCard);
		return newCard.getCardId();
	}

	@Override
	public void updateCustomerCard(CardDTO cardDTO) throws AgroFarmException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		Optional<Card> optionalCard = cardRepository.findById(cardDTO.getCardId());
		Card card = optionalCard.orElseThrow(() -> new AgroFarmException("PaymentService.CARD_NOT_FOUND"));
		cardDTO.setHashCvv(HashingUtility.getHashValue(cardDTO.getCvv().toString()));
		card.setCardId(cardDTO.getCardId());
		card.setNameOnCard(cardDTO.getNameOnCard());
		card.setCardNumber(cardDTO.getCardNumber());
		card.setCardType(cardDTO.getCardType());
		card.setCvv(cardDTO.getHashCvv());
		card.setExpiryDate(cardDTO.getExpiryDate());
		card.setCustomerEmailId(cardDTO.getCustomerEmailId());

	}

	@Override
	public void deleteCustomerCard(String customerEmailId, Integer cardId) throws AgroFarmException {
		// TODO Auto-generated method stub
		List<Card> listOfCustomerCards = cardRepository.findByCustomerEmailId(customerEmailId);
		if (listOfCustomerCards.isEmpty())
			throw new AgroFarmException("PaymentService.CUSTOMER_NOT_FOUND");
		
		Optional<Card> optionalCards = cardRepository.findById(cardId);
		Card card = optionalCards.orElseThrow(() -> new AgroFarmException("PaymentService.CARD_NOT_FOUND"));
		cardRepository.delete(card);

	}

	@Override
	public List<CardDTO> getCustomerCardOfCardType(String customerEmailId, String cardType) throws AgroFarmException {
		// TODO Auto-generated method stub
		
		List<Card> cards = cardRepository.findByCustomerEmailIdAndCardType(customerEmailId, cardType);
		
		if (cards.isEmpty()) {
		throw new AgroFarmException("PaymentService.CARD_NOT_FOUND");
		}
		
		List<CardDTO> cardDTOs = new ArrayList<CardDTO>();
		for (Card card: cards) { 
		
		CardDTO cardDTO = new CardDTO();
		
		cardDTO.setCardId(card.getCardId());
		cardDTO.setNameOnCard(card.getNameOnCard()); 
		cardDTO.setCardNumber(card.getCardNumber());
		cardDTO.setCardType(card.getCardType());
		cardDTO.setHashCvv("XXX"); 
		cardDTO.setExpiryDate(card.getExpiryDate());
		cardDTO.setCustomerEmailId(card.getCustomerEmailId());
		cardDTOs.add(cardDTO);
		
		}

		return cardDTOs;
	}
	
	@Override
	public List<CardDTO> getCardsOfCustomer(String customerEmailId, String cardType) throws AgroFarmException {
		// TODO Auto-generated method stub
		
		List<Card> cards = cardRepository.findByCustomerEmailIdAndCardType(customerEmailId, cardType);
		
		if(cards.isEmpty()) {
			throw new AgroFarmException("PaymentService.CUSTOMER_NOT_FOUND");
		}
		
		List<CardDTO> cardDTOs = new ArrayList<>();
		for(Card card : cards) {
			CardDTO cardDTO = new CardDTO();
			cardDTO.setCardId(card.getCardId());
			cardDTO.setNameOnCard(card.getNameOnCard()); 
			cardDTO.setCardNumber(card.getCardNumber());
			cardDTO.setCardType(card.getCardType());
			cardDTO.setHashCvv("XXX"); 
			cardDTO.setExpiryDate(card.getExpiryDate());
			cardDTO.setCustomerEmailId(card.getCustomerEmailId());
			cardDTOs.add(cardDTO);
		}
		
		return cardDTOs;
	}

	@Override
	public Integer addTransaction(TransactionDTO transactionDTO) throws AgroFarmException {
		// TODO Auto-generated method stub

		if (transactionDTO.getTransactionStatus().equals(TransactionStatus.TRANSACTION_FAILED)) {
			throw new AgroFarmException("PaymentService.TRANSACTION_FAILED_CVV_NOT_MATCHING");
		}

		Transaction transaction = new Transaction();
		transaction.setCardId(transactionDTO.getCard().getCardId());
		transaction.setOrderId(transactionDTO.getOrder().getOrderId());
		transaction.setTotalPrice(transactionDTO.getTotalPrice());
		transaction.setTransactionDate(transactionDTO.getTransactionDate());
		transaction.setTransactionStatus(transactionDTO.getTransactionStatus());
		transactionRepository.save(transaction);

		return transaction.getTransactionId();
	}
	
	@Override
	public CardDTO getCard(Integer cardId) throws AgroFarmException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		Optional<Card> optionalCards = cardRepository.findById(cardId);
		Card card = optionalCards.orElseThrow(() -> new AgroFarmException("PaymentService.CARD_NOT_FOUND"));
		
		CardDTO cardDTO = new CardDTO();
		cardDTO.setCardId(card.getCardId());
		cardDTO.setCustomerEmailId(card.getCustomerEmailId());
		cardDTO.setHashCvv(card.getCvv());
		cardDTO.setCardType(card.getCardType());
		cardDTO.setExpiryDate(card.getExpiryDate());
		cardDTO.setNameOnCard(card.getNameOnCard());
		cardDTO.setCardNumber(card.getCardNumber());		
		
		return cardDTO;
	}

	@Override
	public TransactionDTO authenticatePayment(String customerEmailId, TransactionDTO transactionDTO)
			throws AgroFarmException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		
		if(!transactionDTO.getOrder().getCustomerEmailId().equals(customerEmailId)){
			throw new AgroFarmException("PaymentService.ORDER_DOES_NOT_BELONGS");
		}
		
		if(!transactionDTO.getOrder().getOrderStatus().equals("PLACED")){
			throw new AgroFarmException("PaymentService.TRANSACTION_ALREADY_DONE");
		}
	    
		CardDTO cartDTO = getCard(transactionDTO.getCard().getCardId());
		
		
		if(!cartDTO.getCustomerEmailId().matches(customerEmailId)) {
			throw new AgroFarmException("PaymentService.CARD_DOES_NOT_BELONGS");
		}
		
		if(!cartDTO.getCardType().equals(transactionDTO.getOrder().getPaymentThrough())) {
			throw new AgroFarmException("PaymentService.PAYMENT_OPTION_SELECTED_NOT_MATCHING_CARD_TYPE");
		}
		
		
		if(cartDTO.getHashCvv().equals(HashingUtility.getHashValue(transactionDTO.getCard().getCvv().toString()))) {
			transactionDTO.setTransactionStatus(TransactionStatus.TRANSACTION_SUCCESS);
		}else {
			transactionDTO.setTransactionStatus(TransactionStatus.TRANSACTION_FAILED);
		}
		
		return transactionDTO;
	}

}

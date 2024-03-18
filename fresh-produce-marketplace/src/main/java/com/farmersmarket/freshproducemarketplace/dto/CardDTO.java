package com.farmersmarket.freshproducemarketplace.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDTO {

	private String cardType;
	
	private String cardNumber;
	
	private String nameOnCard;
	
	private String hashCvv;
	
	@NotNull(message = "{transaction.cvv.notpresent}")
	private String cvv;
	
	private LocalDate expiryDate;
	
	@NotNull(message = "{transaction.cardId.notpresent}")
	private Integer cardId;
	
	private String  customerEmailId;

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getHashCvv() {
		return hashCvv;
	}

	public void setHashCvv(String hashCvv) {
		this.hashCvv = hashCvv;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	@Override
	public String toString() {
		return "CardDTO [cardType=" + cardType + ", cardNumber=" + cardNumber + ", nameOnCard=" + nameOnCard
				+ ", hashCvv=" + hashCvv + ", cvv=" + cvv + ", expiryDate=" + expiryDate + ", cardId=" + cardId
				+ ", customerEmailId=" + customerEmailId + "]";
	}
	
	

}

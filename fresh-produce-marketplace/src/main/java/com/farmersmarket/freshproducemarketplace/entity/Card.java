package com.farmersmarket.freshproducemarketplace.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AF_CARD")
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CARD_ID")
	private Integer cardId;
	
	@Column(name = "CARD_TYPE")
	private String cardType;
	
	@Column(name = "CARD_NUMBER", nullable = false, length = 16)
	private String cardNumber;
	
	@Column(name = "CVV", nullable = false, length = 70)
	private String cvv;
	
	@Column(name = "EXPIRY_DATE")
	private LocalDate expiryDate;
	
	@Column(name = "NAME_ON_CARD", nullable = false, length = 50)
    private String nameOnCard;

    @Column(name = "CUSTOMER_EMAIL_ID", length = 58)
    private String customerEmailId;

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

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

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate localDate) {
		this.expiryDate = localDate;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	
    

}

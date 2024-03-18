package com.farmersmarket.freshproducemarketplace.dto;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerCartDTO {
	
	private Integer cardId;
	
	@NotNull(message = "{customeremail.absent}")
	@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-z]{2,}\\.[a-zA-Z][a-zA-Z.]+",message = "{invalid.customeremail.format}")
	private String customerEmailId;
	
	@Valid
	private Set<CartProductDTO> cartProducts;

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

	public Set<CartProductDTO> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(Set<CartProductDTO> cartProducts) {
		this.cartProducts = cartProducts;
	}
	
	
}

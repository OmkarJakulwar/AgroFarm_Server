package com.farmersmarket.freshproducemarketplace.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class OrderDTO {
	
	private Long orderId;
	
	@NotNull(message = "{email.absent}")
	@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-z]{2,}\\.[a-zA-Z][a-zA-Z.]+",message = "{invalid.email.format}")
	private String customerEmailId;
	
	private LocalDateTime dateOfOrder;
	
	private Double totalPrice;
	
	private String  orderStatus;
	
	private Double discount;
	
	@NotNull(message = "{order.paymentthrough.absent}")
	@Pattern(regexp = "(DEBIT_CARD|CREDIT_CARD)",message = "{order.paymentthrough.invalid}")
	private String paymentThrough;
	
	@NotNull(message = "{order.dateofdelivery.absent}")
	@Future(message = "{order.dateofdelivery.invalid}")
	private LocalDateTime dateOfDelivery;
	
	private String deliveryAddress;
	
	private List<OrderedProductDTO> orderProducts;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	public LocalDateTime getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(LocalDateTime dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getPaymentThrough() {
		return paymentThrough;
	}

	public void setPaymentThrough(String paymentThrough2) {
		this.paymentThrough = paymentThrough2;
	}

	public LocalDateTime getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(LocalDateTime dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<OrderedProductDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderedProductDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}
	
	
	
}

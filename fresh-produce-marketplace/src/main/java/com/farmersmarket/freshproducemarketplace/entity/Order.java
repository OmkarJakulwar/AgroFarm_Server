package com.farmersmarket.freshproducemarketplace.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.farmersmarket.freshproducemarketplace.dto.OrderStatus;
import com.farmersmarket.freshproducemarketplace.dto.PaymentThrough;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "AF_ORDER")
public class Order {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;
	
	@Column(name = "DATE_OF_ORDER", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateOfOrder;

	@Column(name = "TOTAL_PRICE", nullable = false)
    private Double totalPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ORDER_STATUS")
    private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_THROUGH")
    private PaymentThrough paymentThrough;
	
	@Column(name = "DATE_OF_DELIVERY")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateOfDelivery;
	
	@Column(name = "CUSTOMER_EMAIL_ID", length = 50)
    private String customerEmailId;
	
	@Column(name = "DISCOUNT")
    private Double discount;
	
	@Column(name = "DELIVERY_ADDRESS", length = 500)
    private String deliveryAddress;
	
	@OneToMany(mappedBy = "Order", cascade = CascadeType.ALL)
    private List<OrderedProduct> orderedProducts;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	
	

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	

	public PaymentThrough getPaymentThrough() {
		return paymentThrough;
	}

	public void setPaymentThrough(PaymentThrough paymentThrough) {
		this.paymentThrough = paymentThrough;
	}

	public LocalDateTime getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(LocalDateTime dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public String getCustomerEmailId() {
		return customerEmailId;
	}

	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<OrderedProduct> getOrderedProducts() {
		return orderedProducts;
	}

	public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}
	
	
}

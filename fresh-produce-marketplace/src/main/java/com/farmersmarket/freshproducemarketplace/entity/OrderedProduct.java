package com.farmersmarket.freshproducemarketplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "AF_ORDERED_PRODUCT")
public class OrderedProduct {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERED_PRODUCT_ID")
    private Integer orderedProductId;
	
	@ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order Order;
	
	@Column(name = "PRODUCT_ID", nullable = false)
    private Integer productId;
	
	@Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

	public Integer getOrderedProductId() {
		return orderedProductId;
	}

	public void setOrderedProductId(Integer orderedProductId) {
		this.orderedProductId = orderedProductId;
	}

	public Order getOrder() {
		return Order;
	}

	public void setOrder(Order order) {
		Order = order;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

}

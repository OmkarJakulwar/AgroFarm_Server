package com.farmersmarket.freshproducemarketplace.service;

import java.util.List;

import com.farmersmarket.freshproducemarketplace.dto.OrderDTO;
import com.farmersmarket.freshproducemarketplace.dto.OrderStatus;
import com.farmersmarket.freshproducemarketplace.dto.PaymentThrough;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;

public interface CustomerOrderService {
	
	Long placeOrder (OrderDTO orderDTO) throws AgroFarmException;
	
	OrderDTO getOrderDetails(Long orderId) throws AgroFarmException;
	
	List<OrderDTO> findOrdersByCustomerEmailId(String emailId) throws AgroFarmException;
	
	void updateOrderStatus(Long orderId, OrderStatus orderStatus) throws AgroFarmException;
	
	void updatePaymentThrough(Long orderId, PaymentThrough paymentThrough) throws AgroFarmException;
	
    List<OrderDTO> getOrdersByCustomerAndOrderId(String customerEmailId, Long orderId) throws AgroFarmException;

	
}

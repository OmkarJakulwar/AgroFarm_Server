package com.farmersmarket.freshproducemarketplace.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmersmarket.freshproducemarketplace.dto.CustomerDTO;
import com.farmersmarket.freshproducemarketplace.dto.OrderDTO;
import com.farmersmarket.freshproducemarketplace.dto.OrderStatus;
import com.farmersmarket.freshproducemarketplace.dto.OrderedProductDTO;
import com.farmersmarket.freshproducemarketplace.dto.PaymentThrough;
import com.farmersmarket.freshproducemarketplace.dto.ProductDTO;
import com.farmersmarket.freshproducemarketplace.entity.Order;
import com.farmersmarket.freshproducemarketplace.entity.OrderedProduct;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.repository.CustomerOrderRepository;

@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {
	
	@Autowired
	private CustomerOrderRepository orderRepository;
	
	@Autowired
	private CustomerService customerService;

	@Override
	public Long placeOrder(OrderDTO orderDTO) throws AgroFarmException {
		// TODO Auto-generated method stub
		
		CustomerDTO customerDTO = customerService.getExistingCustomer(orderDTO.getCustomerEmailId());
		
		if(customerDTO.getAddress() == null || customerDTO.getAddress().isBlank()) {
			throw new AgroFarmException("OrderService.ADDRESS_NOT_AVAILABLE");
		}
		
		Order order = new Order();
		order.setDeliveryAddress(customerDTO.getAddress());
		order.setCustomerEmailId(orderDTO.getCustomerEmailId());
		order.setDateOfDelivery(orderDTO.getDateOfDelivery());
		order.setDateOfOrder(LocalDateTime.now());
		order.setPaymentThrough(PaymentThrough.valueOf(orderDTO.getPaymentThrough()));
		
		if(order.getPaymentThrough().equals(PaymentThrough.CREDIT_CARD)) {
			order.setDiscount(10.00d);
		} else {
			order.setDiscount(5.00d);
		}
		
		order.setOrderStatus(OrderStatus.PLACED);
		Double price = 0.0;
		List<OrderedProduct> orderedProducts = new ArrayList<OrderedProduct>();
		
		for(OrderedProductDTO orderedProductDTO : orderDTO.getOrderProducts()) {
	
			
			if(orderedProductDTO.getProduct().getAvailableQuantity() < orderedProductDTO.getQuantity()) {
				throw new AgroFarmException("OrderService.INSUFFICIENT_STOCK");
			}
		
			OrderedProduct orderedProduct = new OrderedProduct();
			orderedProduct.setProductId(orderedProductDTO.getProduct().getProductId());
			orderedProduct.setQuantity(orderedProductDTO.getQuantity());
			orderedProducts.add(orderedProduct);
			price = price + orderedProductDTO.getQuantity() * orderedProductDTO.getProduct().getPrice();
			
		}
		
		order.setOrderedProducts(orderedProducts);
		
		order.setTotalPrice(price * (100 - order.getDiscount()) / 100);
		
		orderRepository.save(order);
		
		return order.getOrderId();
	}

	@Override
	public OrderDTO getOrderDetails(Long orderId) throws AgroFarmException {
		// TODO Auto-generated method stub
		
		   // Find the order by orderId
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        // If the order is not found, throw an exception
        Order order = orderOptional.orElseThrow(() ->
                new AgroFarmException("OrderService.ORDER_NOT_FOUND" + orderId));

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderId);
        orderDTO.setCustomerEmailId(order.getCustomerEmailId());
        orderDTO.setDateOfDelivery(order.getDateOfDelivery());
        orderDTO.setDateOfOrder(order.getDateOfOrder());
        orderDTO.setPaymentThrough(order.getPaymentThrough().toString());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderStatus(order.getOrderStatus().toString());
        orderDTO.setDeliveryAddress(order.getDeliveryAddress());
        orderDTO.setDiscount(order.getDiscount());
        
        List<OrderedProductDTO> orderedProductDTOs = new ArrayList<OrderedProductDTO>();
        for(OrderedProduct orderedProduct : order.getOrderedProducts()) {
        	OrderedProductDTO orderedProductDTO = new OrderedProductDTO();
        	ProductDTO productDTO = new ProductDTO();
        	productDTO.setProductId(orderedProduct.getProductId());
        	orderedProductDTO.setOrderedProductId(orderedProduct.getOrderedProductId());
        	orderedProductDTO.setQuantity(orderedProduct.getQuantity());
        	orderedProductDTO.setProduct(productDTO);
        	orderedProductDTOs.add(orderedProductDTO);
        }
        orderDTO.setOrderProducts(orderedProductDTOs);
		return orderDTO;
	}
	
	@Override
	public void updateOrderStatus(Long orderId, OrderStatus orderStatus) throws AgroFarmException {
		// TODO Auto-generated method stub
		
		 Optional<Order> orderOptional = orderRepository.findById(orderId);

	        // If the order is not found, throw an exception
	        Order order = orderOptional.orElseThrow(() ->
	                new AgroFarmException("OrderService.ORDER_NOT_FOUND" + orderId));
	        
	       order.setOrderStatus(orderStatus);
	       order.setDateOfOrder(LocalDateTime.now());
	       order.setDateOfDelivery(LocalDateTime.now().plusDays(7));
	}

	@Override
	public void updatePaymentThrough(Long orderId, PaymentThrough paymentThrough) throws AgroFarmException {
		// TODO Auto-generated method stub
		
		Optional<Order> orderOptional = orderRepository.findById(orderId);

        // If the order is not found, throw an exception
        Order order = orderOptional.orElseThrow(() ->
                new AgroFarmException("OrderService.ORDER_NOT_FOUND" + orderId));
        
        if(order.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
        	throw new AgroFarmException("OrderService.TRANSACTION_ALREADY_DONE");
        }
        
        order.setPaymentThrough(paymentThrough);
		
	}

	@Override
	public List<OrderDTO> findOrdersByCustomerEmailId(String emailId) throws AgroFarmException {
		// TODO Auto-generated method stub
		
		List<Order> orders = orderRepository.findByCustomerEmailId(emailId);
		if(orders.isEmpty()) {
			throw new AgroFarmException("OrderService.NO_ORDERS_FOUND");
		}
		List<OrderDTO> orderDTOs = new ArrayList<>();
		for(Order order : orders) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderId(order.getOrderId());
			orderDTO.setCustomerEmailId(order.getCustomerEmailId());
			orderDTO.setDateOfDelivery(order.getDateOfDelivery());
			orderDTO.setDateOfOrder(order.getDateOfOrder());
			orderDTO.setPaymentThrough(order.getPaymentThrough().toString());
			orderDTO.setTotalPrice(order.getTotalPrice());
			orderDTO.setOrderStatus(order.getOrderStatus().toString());
			orderDTO.setDiscount(order.getDiscount());
			List<OrderedProductDTO> orderedProductDTOs = new ArrayList<OrderedProductDTO>();
			for(OrderedProduct orderedProduct : order.getOrderedProducts()) {
				OrderedProductDTO orderedProductDTO = new OrderedProductDTO();
	        	ProductDTO productDTO = new ProductDTO();
	        	productDTO.setProductId(orderedProduct.getProductId());
	        	orderedProductDTO.setOrderedProductId(orderedProduct.getOrderedProductId());
	        	orderedProductDTO.setQuantity(orderedProduct.getQuantity());
	        	orderedProductDTO.setProduct(productDTO);
	        	orderedProductDTOs.add(orderedProductDTO);
			}
			orderDTO.setOrderProducts(orderedProductDTOs);
			orderDTO.setDeliveryAddress(order.getDeliveryAddress());
			orderDTOs.add(orderDTO);
		}
		
		return orderDTOs;
	}
	
	@Override
	public List<OrderDTO> getOrdersByCustomerAndOrderId(String customerEmailId, Long orderId) throws AgroFarmException {
        // Add your logic to retrieve orders by customerEmailId and orderId from the repository
        // For example:
        List<Order> orders = orderRepository.findByCustomerEmailIdAndOrderId(customerEmailId, orderId);
        if (orders.isEmpty()) {
            throw new AgroFarmException("OrderService.ORDER_NOT_FOUND");
        }

        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            // Map Order properties to OrderDTO
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setCustomerEmailId(order.getCustomerEmailId());
            orderDTO.setDateOfDelivery(order.getDateOfDelivery());
			orderDTO.setDateOfOrder(order.getDateOfOrder());
			orderDTO.setPaymentThrough(order.getPaymentThrough().toString());
			orderDTO.setTotalPrice(order.getTotalPrice());
			orderDTO.setOrderStatus(order.getOrderStatus().toString());
			orderDTO.setDiscount(order.getDiscount());
			List<OrderedProductDTO> orderedProductDTOs = new ArrayList<OrderedProductDTO>();
			for(OrderedProduct orderedProduct : order.getOrderedProducts()) {
				OrderedProductDTO orderedProductDTO = new OrderedProductDTO();
	        	ProductDTO productDTO = new ProductDTO();
	        	productDTO.setProductId(orderedProduct.getProductId());
	        	orderedProductDTO.setOrderedProductId(orderedProduct.getOrderedProductId());
	        	orderedProductDTO.setQuantity(orderedProduct.getQuantity());
	        	orderedProductDTO.setProduct(productDTO);
	        	orderedProductDTOs.add(orderedProductDTO);
			}
			orderDTO.setOrderProducts(orderedProductDTOs);
			orderDTO.setDeliveryAddress(order.getDeliveryAddress());
			orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

}

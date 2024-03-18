package com.farmersmarket.freshproducemarketplace.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.farmersmarket.freshproducemarketplace.dto.CartProductDTO;
import com.farmersmarket.freshproducemarketplace.dto.OrderDTO;
import com.farmersmarket.freshproducemarketplace.dto.OrderStatus;
import com.farmersmarket.freshproducemarketplace.dto.OrderedProductDTO;
import com.farmersmarket.freshproducemarketplace.dto.PaymentThrough;
import com.farmersmarket.freshproducemarketplace.dto.ProductDTO;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.service.CustomerOrderService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/order-api")
public class OrderController {

	@Autowired
	private CustomerOrderService orderService;

	@Autowired
	private Environment environment;

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/place-order")
	public ResponseEntity<String> placeOrder(@Valid @RequestBody OrderDTO order) throws AgroFarmException {

		ResponseEntity<CartProductDTO[]> cartProductDTOsResponse = restTemplate.getForEntity(
				"http://localhost:8080/AgroFarm/cart-api/customer/" + order.getCustomerEmailId() + "/products",
				CartProductDTO[].class);

		CartProductDTO[] cartProductDTOs = cartProductDTOsResponse.getBody();
		restTemplate
				.delete("http://localhost:8080/AgroFarm/cart-api/customer/" + order.getCustomerEmailId() + "/products");
		List<OrderedProductDTO> orderedProductDTOs = new ArrayList<>();

		for (CartProductDTO cartProductDTO : cartProductDTOs) {
			OrderedProductDTO orderProductDTO = new OrderedProductDTO();
			orderProductDTO.setProduct(cartProductDTO.getProduct());
			orderProductDTO.setQuantity(cartProductDTO.getQuantity());
			orderedProductDTOs.add(orderProductDTO);
		}
		order.setOrderProducts(orderedProductDTOs);

		Long orderId = orderService.placeOrder(order);
		String modificationSuccessMessage = environment.getProperty("OrderAPT.ORDERED_PLACE_SUCCESSFULLY");

		return new ResponseEntity<String>(modificationSuccessMessage + orderId, HttpStatus.OK);
	}

	@GetMapping(value = "/customer/{customerEmailId}/orders")
	public ResponseEntity<List<OrderDTO>> getOrdersOfCustomer(
			@NotNull(message = "[email.absent}") @PathVariable String customerEmailId) throws AgroFarmException {

		List<OrderDTO> orderDTOs = orderService.findOrdersByCustomerEmailId(customerEmailId);

		for (OrderDTO orderDTO : orderDTOs) {
			for (OrderedProductDTO orderedProductDTO : orderDTO.getOrderProducts()) {

				ResponseEntity<ProductDTO> productResponse = restTemplate
						.getForEntity("http://localhost:8080/AgroFarm/product-api/product/"
								+ orderedProductDTO.getProduct().getProductId(), ProductDTO.class);

				orderedProductDTO.setProduct(productResponse.getBody());

			}
		}
		return new ResponseEntity<List<OrderDTO>>(orderDTOs, HttpStatus.OK);

	}

	@GetMapping("/customer/{customerEmailId}/order/{orderId}")
	public ResponseEntity<List<OrderDTO>> getOrdersByCustomerAndOrderId(@PathVariable String customerEmailId,
			@PathVariable Long orderId) throws AgroFarmException {
		List<OrderDTO> orderDTOs = orderService.getOrdersByCustomerAndOrderId(customerEmailId, orderId);

		for (OrderDTO orderDTO : orderDTOs) {
			for (OrderedProductDTO orderedProductDTO : orderDTO.getOrderProducts()) {

				ResponseEntity<ProductDTO> productResponse = restTemplate
						.getForEntity("http://localhost:8080/AgroFarm/product-api/product/"
								+ orderedProductDTO.getProduct().getProductId(), ProductDTO.class);

				orderedProductDTO.setProduct(productResponse.getBody());

			}
		}
		return new ResponseEntity<List<OrderDTO>>(orderDTOs, HttpStatus.OK);

	}

	@PutMapping(value = "/order/{orderId}/update/order-status")
	public void updateOrderAfterPayment(@NotNull(message = "{orderId.absent}") @PathVariable Long orderId,
			@RequestBody String transactionStatus) throws AgroFarmException {

		if (transactionStatus.equals("TRANSACTION_SUCCESS")) {
			orderService.updateOrderStatus(orderId, OrderStatus.CONFIRMED);
			OrderDTO orderDTO = orderService.getOrderDetails(orderId);

			for (OrderedProductDTO orderedProductDTO : orderDTO.getOrderProducts()) {

				restTemplate.put("http://localhost:8080/AgroFarm/product-api/updatequantity/"
						+ orderedProductDTO.getProduct().getProductId(), orderedProductDTO.getQuantity());

			}
		} else

		{
			orderService.updateOrderStatus(orderId, OrderStatus.CANCELLED);
		}
	}

	@PutMapping(value = "/order/{orderId}/update/payment-through")
	public void updatePaymentOption(@NotNull(message = "[orderId.absent}") @PathVariable Long orderId,
			@RequestBody String paymentThrough) throws AgroFarmException {
		if (paymentThrough.equals("DEBIT_CARD")) {
			orderService.updatePaymentThrough(orderId, PaymentThrough.DEBIT_CARD);
		} else

		{
			orderService.updatePaymentThrough(orderId, PaymentThrough.CREDIT_CARD);
		}
	}

	@GetMapping("order/{orderId}")
	public ResponseEntity<OrderDTO> getOrderDetails(@NotNull(message = "{orderId.absent}") @PathVariable Long orderId)
			throws AgroFarmException {

		OrderDTO orderDTO = orderService.getOrderDetails(orderId);
		for (OrderedProductDTO orderedProductDTO : orderDTO.getOrderProducts()) {

			ResponseEntity<ProductDTO> productResponse = restTemplate
					.getForEntity("http://localhost:8080/AgroFarm/product-api/product/"
							+ orderedProductDTO.getProduct().getProductId(), ProductDTO.class);
			orderedProductDTO.setProduct(productResponse.getBody());
		}

		return new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.OK);

	}

}

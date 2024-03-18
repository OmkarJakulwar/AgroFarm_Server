package com.farmersmarket.freshproducemarketplace.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmersmarket.freshproducemarketplace.dto.CartProductDTO;
import com.farmersmarket.freshproducemarketplace.dto.CustomerCartDTO;
import com.farmersmarket.freshproducemarketplace.dto.ProductDTO;
import com.farmersmarket.freshproducemarketplace.entity.CartProduct;
import com.farmersmarket.freshproducemarketplace.entity.CustomerCart;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.repository.CartProductRepository;
import com.farmersmarket.freshproducemarketplace.repository.CustomerCartRepository;

@Service
@Transactional
public class CustomerCartServiceImpl implements CustomerCartService {

	@Autowired
	private CustomerCartRepository customerCartRepository;

	@Autowired
	private CartProductRepository cartProductRepository;

	@Override
	public Integer addProductToCart(CustomerCartDTO customerCartDTO) throws AgroFarmException {

		
		// Cart Products setting
		Set<CartProduct> cartProducts = new HashSet<>();
		Integer cartId = null;
		// getting products from cart
	      if(!customerCartDTO.getCartProducts().isEmpty()) {
			for (CartProductDTO cartProductDTO : customerCartDTO.getCartProducts()) {
				CartProduct cartProduct = new CartProduct();
				cartProduct.setProductId(cartProductDTO.getProduct().getProductId());
				cartProduct.setQuantity(cartProductDTO.getQuantity());
				// adding products to cart
				cartProducts.add(cartProduct);
			}
		} else {
			throw new AgroFarmException("Cart products cannot be empty");
		}
			
		// Finding the Customer Cart from the Cusotmer Email Id :
		Optional<CustomerCart> findingCustomerCart = customerCartRepository
				.findByCustomerEmailId(customerCartDTO.getCustomerEmailId());
		// If Customer Cart is Empty
		if (findingCustomerCart.isEmpty()) {
			CustomerCart newCustomerCart = new CustomerCart();
			newCustomerCart.setCustomerEmailId(customerCartDTO.getCustomerEmailId());
			// Adding new Products to the Cart
			newCustomerCart.setCartProducts(cartProducts);
			customerCartRepository.save(newCustomerCart);
			cartId = newCustomerCart.getCartId();
		} else {
			// Fetching the Customer Cart6+
			CustomerCart cart = findingCustomerCart.get();
			// Customer product to be added which are already found in the cart
			for (CartProduct cartProductToBeAdded : cartProducts) {
				Boolean found = false;
				for (CartProduct cartProductFromCart : cart.getCartProducts()) {
					// If Already found cart products from cart is equal to the cart products to be
					// added Then:
					if (cartProductFromCart.equals(cartProductToBeAdded)) {
						// Updating the Quantitiy of the product which are already present in the cart
						cartProductFromCart
								.setQuantity(cartProductToBeAdded.getQuantity() + cartProductFromCart.getQuantity());
						found = true;
					}
				}
				// If customer Product not found in Customer cart then :
				if (found == false) {
					// // Adding new Product to be added in the Cusotmer cart
					cart.getCartProducts().add(cartProductToBeAdded);
				}
			}
			// Finally returning the CartId has successfully added the Product to the cart
			cartId = cart.getCartId();
		}

		return cartId;
	}

	@Override
	public Set<CartProductDTO> getProductsFromCart(String customerEmailId) throws AgroFarmException {
		// TODO Auto-generated method stub
		Optional<CustomerCart> findingCustomerCart = customerCartRepository.findByCustomerEmailId(customerEmailId);

		// If the cart is not found, throw an exception
		CustomerCart cart = findingCustomerCart
				.orElseThrow(() -> new AgroFarmException("CustomerCartService.NO_CART_FOUND" + customerEmailId));

		if (cart.getCartProducts().isEmpty()) {
			new AgroFarmException("CustomerCartService.NO_PRODUCT_ADDED_TO_CART" + customerEmailId);

		}

		Set<CartProductDTO> cartProductDTOs = cart.getCartProducts().stream().map(cartProduct -> {
			CartProductDTO cartProductDTO = new CartProductDTO();
//                    if(cart.getCartProducts() != null) {
			cartProductDTO.setCartProductId(cartProduct.getCartProductId());
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductId(cartProduct.getProductId());
			productDTO.setQuantity(cartProduct.getQuantity());
//                 
			cartProductDTO.setProduct(productDTO);
			cartProductDTO.setQuantity(cartProduct.getQuantity());
//                    } 
			// You can set other properties as needed
			return cartProductDTO;
		}).collect(Collectors.toSet());

		return cartProductDTOs;

	}

	@Override
	public void deleteProductFromCart(String customerEmailId, Integer productId) throws AgroFarmException {
		// TODO Auto-generated method stub

		// Fetch the Customer Cart from the Customer Email Id
		Optional<CustomerCart> findingCustomerCart = customerCartRepository.findByCustomerEmailId(customerEmailId);

		// If the cart is not found, throw an exception
		CustomerCart cart = findingCustomerCart.orElseThrow(
				() -> new AgroFarmException("Cart not found for the given customer email id: " + customerEmailId));
		// Find the CartProduct with the specified product ID in the cart
		Optional<CartProduct> productToDelete = cart.getCartProducts().stream()
				.filter(cartProduct -> cartProduct.getProductId().equals(productId)).findFirst();

		// If the product is not found in the cart, throw an exception
		CartProduct cartProduct = productToDelete.orElseThrow(
				() -> new AgroFarmException("Product not found in the cart with product ID: " + productId));

		// Remove the product from the cart
		cart.getCartProducts().remove(cartProduct);

		// Delete the CartProduct from the database
		cartProductRepository.deleteById(cartProduct.getCartProductId());

		// Update the cart in the database
		customerCartRepository.save(cart);

	}

	@Override
	public void deleteAllProductsFromCart(String customerEmailId) throws AgroFarmException {
		// TODO Auto-generated method stub
		Optional<CustomerCart> cartOptional = customerCartRepository.findByCustomerEmailId(customerEmailId);
		CustomerCart cart = cartOptional.orElseThrow(() -> new AgroFarmException("CustomerCartService.NO_CART_FOUND"));

		if (cart.getCartProducts().isEmpty()) {
			throw new AgroFarmException("CustomerCartService.NO_PRODUCT_ADDED_TO_CART");
		}

		// Delete all CartProducts from the database
		cart.getCartProducts().forEach(cartProduct -> cartProductRepository.deleteById(cartProduct.getCartProductId()));

		// Clear the list of cart products in-memory
		cart.getCartProducts().clear();

		// Update the cart in the database
		customerCartRepository.save(cart);

	}

	@Override
	public void modifyQuantityOfProductInCart(String customerEmailId, Integer productId, Integer quantity)
			throws AgroFarmException {
		// TODO Auto-generated method stub

		Optional<CustomerCart> cartOptional = customerCartRepository.findByCustomerEmailId(customerEmailId);
		CustomerCart cart = cartOptional.orElseThrow(() -> new AgroFarmException("CustomerCartService.NO_CART_FOUND"));

		if (cart.getCartProducts().isEmpty()) {
			throw new AgroFarmException("CustomerCartService.NO_PRODUCT_ADDED_TO_CART");
		}
		CartProduct selectedProduct = null;
		for (CartProduct product : cart.getCartProducts()) {
			if (product.getProductId().equals(productId)) {
				selectedProduct = product;
			}
		}
		if (selectedProduct == null) {
			throw new AgroFarmException("CustomerCartService.PRODUCT_ALREADY_NOT_AVAILABLE");
		}
		selectedProduct.setQuantity(quantity);

	}

}

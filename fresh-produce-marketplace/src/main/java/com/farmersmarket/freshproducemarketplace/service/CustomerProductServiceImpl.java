package com.farmersmarket.freshproducemarketplace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.farmersmarket.freshproducemarketplace.dto.ProductDTO;
import com.farmersmarket.freshproducemarketplace.entity.Product;
import com.farmersmarket.freshproducemarketplace.exception.AgroFarmException;
import com.farmersmarket.freshproducemarketplace.repository.ProductRepository;

@Service
@Transactional
public class CustomerProductServiceImpl implements CustomerProductService {
	
	@Autowired
	private ProductRepository productRepository;

	
	@Override
	public List<ProductDTO> getAllProducts() throws AgroFarmException {
		// TODO Auto-generated method stub
		List<Product> products = (List<Product>) productRepository.findAll();
		if(products.isEmpty()) {
			throw new AgroFarmException("ProductService.PRODUCT_NOT_AVAILABLE");
		}
		List<ProductDTO> productDTOs = new ArrayList<>();
		for(Product product : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductId(product.getProductId());
			productDTO.setName(product.getName());
			productDTO.setPrice(product.getPrice());
			productDTO.setCategory(product.getCategory());
			productDTO.setAvailableQuantity(product.getAvailableQuantity());
			productDTO.setDescription(product.getDescription());
			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	@Override
	public ProductDTO getProductById(Integer productId) throws AgroFarmException {
		// TODO Auto-generated method stub
		Optional<Product> product1 = productRepository.findById(productId);
		Product product = product1.orElseThrow(()-> new AgroFarmException("ProductService.PRODUCT_NOT_AVAILABLE"));
	
		if(product == null) {
			throw new AgroFarmException("Product not found...!!");
		}
		
		ProductDTO productDto = new ProductDTO();
		productDto.setProductId(product.getProductId());
		productDto.setName(product.getName());
		productDto.setDescription(product.getDescription());
		productDto.setCategory(product.getCategory());
		productDto.setPrice(product.getPrice());
		productDto.setAvailableQuantity(product.getAvailableQuantity());
					
		return productDto;
	}

	@Override
	public void reduceAvailableQuantity(Integer productId, Integer quantity) throws AgroFarmException {
		// TODO Auto-generated method stub
		Optional<Product> product1 = productRepository.findById(productId);
		Product product = product1.orElseThrow(()-> new AgroFarmException("ProductService.PRODUCT_NOT_AVAILABLE"));
		
		product.setAvailableQuantity(product.getAvailableQuantity()-quantity);
		
	}


}

package com.farmersmarket.freshproducemarketplace.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farmersmarket.freshproducemarketplace.entity.CartProduct;

@Repository
public interface CartProductRepository extends CrudRepository<CartProduct, Integer> {

}

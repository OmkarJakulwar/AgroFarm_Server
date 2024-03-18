package com.farmersmarket.freshproducemarketplace.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farmersmarket.freshproducemarketplace.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{

}

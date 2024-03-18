package com.farmersmarket.freshproducemarketplace.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farmersmarket.freshproducemarketplace.entity.Order;

@Repository
public interface CustomerOrderRepository extends CrudRepository<Order, Long>{

	List<Order> findByCustomerEmailId(String customerEmailId);

	List<Order> findByCustomerEmailIdAndOrderId(String customerEmailId, Long orderId);
}

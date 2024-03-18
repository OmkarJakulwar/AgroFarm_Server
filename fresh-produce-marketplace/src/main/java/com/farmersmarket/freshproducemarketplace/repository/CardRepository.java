package com.farmersmarket.freshproducemarketplace.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farmersmarket.freshproducemarketplace.entity.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>{
	
	List<Card> findByCustomerEmailId(String customerEmailId);
	
	List<Card> findByCustomerEmailIdAndCardType(String customerEmailId, String cardType);

}

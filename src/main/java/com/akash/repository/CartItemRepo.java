package com.akash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.model.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
	
	

}

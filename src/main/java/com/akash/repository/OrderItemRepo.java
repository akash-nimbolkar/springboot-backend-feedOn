package com.akash.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.akash.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

	
}

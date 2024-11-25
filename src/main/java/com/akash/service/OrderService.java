package com.akash.service;

import java.util.List;

import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.stereotype.Service;

import com.akash.model.Order;
import com.akash.model.User;
import com.akash.request.OrderRequest;


public interface OrderService {
	
	public Order createOrder(OrderRequest order,User user) throws Exception;
	
	public Order updateOrder(Long orderId,String  orderStatus) throws Exception;
	
	public void cancelOrder(Long orderId) throws Exception;

	public List<Order> getUsersOrder(Long userId)throws Exception;
	
	public List<Order> getRestaurantsOrder(Long restaurantId,String orderStatus)throws Exception;

	public Order findOrderById(Long orderId)throws Exception;
}
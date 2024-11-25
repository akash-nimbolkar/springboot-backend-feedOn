package com.akash.service;

import java.util.List;

import com.akash.dto.RestaurantDto;
import com.akash.model.Restaurant;
import com.akash.model.User;
import com.akash.request.CreateRestaurantRequest;

public interface RestaurantService {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);

	public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updateRestaurant) throws Exception; 

	public void deleteRestaurant(Long restaurantId) throws Exception;

	public List<Restaurant> getAllRestaurant();
	
	public List<Restaurant> searchRestaurant(String keyword);
	
	public Restaurant findRestaurantById(Long id) throws Exception;
	
	public Restaurant getRestaurantBYUserId(Long userId) throws Exception;

	public RestaurantDto addFavourites(Long restaurantId, User user) throws Exception;

	public Restaurant updateRestaurantStatus(Long id)throws Exception;
}

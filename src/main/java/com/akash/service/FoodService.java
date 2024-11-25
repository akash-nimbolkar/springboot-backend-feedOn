package com.akash.service;

import java.util.List;

import com.akash.model.Category;
import com.akash.model.Food;
import com.akash.model.Restaurant;
import com.akash.request.CreateFoodRequest;

public interface FoodService {
	
	public Food createFood(CreateFoodRequest req, Long categoryId,Restaurant restaurant);
	
	void deleteFood(Long foodId) throws Exception;
	
	public List<Food > getRestaurantsFood(Long restaurantId, boolean isVegeterian, 
										  boolean isNonveg,boolean isSeasonal,
										  String foodCategory);
	
	public List<Food> searchFood(String food);
	
	public Food findFoodById(Long foodId)throws Exception;
	
	public Food updateAvailabilityStatus(Long foodId)throws Exception;
	
	
	

}

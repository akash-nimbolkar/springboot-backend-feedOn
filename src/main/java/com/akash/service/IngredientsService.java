package com.akash.service;

import java.util.List;

import com.akash.model.IngredientsCategory;
import com.akash.model.IngredientsItem;



public interface IngredientsService {
	
	public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception;
	
	 public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception;

	 public List<IngredientsCategory> findIngredientsCategoryByRestaurantId (Long Id) throws Exception;
	 
	 public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName,Long categoryId)throws Exception;
	 
	 public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId);
	 
	 public IngredientsItem updateStock(Long id) throws Exception;
}

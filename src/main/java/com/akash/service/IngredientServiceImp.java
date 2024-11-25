package com.akash.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.model.IngredientsCategory;
import com.akash.model.IngredientsItem;
import com.akash.model.Restaurant;
import com.akash.repository.IngredientCategoryRepository;
import com.akash.repository.IngredientItemRepository;


@Service
public class IngredientServiceImp implements IngredientsService {

	@Autowired
	private IngredientItemRepository ingredientItemRepository;
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	@Override
	public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		IngredientsCategory category = new IngredientsCategory();
		category.setRestaurant(restaurant);
		category.setName(name);
		
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception {
		Optional<IngredientsCategory> opt= ingredientCategoryRepository.findById(id);
		
		if(opt.isEmpty())
		{
			throw new Exception("Ingredient category not found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
		restaurantService.findRestaurantById(id);
		return ingredientCategoryRepository.findByRestaurantId(id);
	}

	@Override
	public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		IngredientsCategory category = findIngredientsCategoryById(categoryId);
		
		IngredientsItem item = new IngredientsItem();
		item.setName(ingredientName);
		item.setRestaurant(restaurant);
		item.setCategory(category);
		
		IngredientsItem ingredient = ingredientItemRepository.save(item);
		category.getIngredients().add(ingredient);
		return ingredient;
	}

	@Override
	public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
		// TODO Auto-generated method stub
		return ingredientItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		Optional<IngredientsItem> optinalIngredientsItem = ingredientItemRepository.findById(id);
		
		if(optinalIngredientsItem.isEmpty())
		{
			throw new Exception("Ingredient not found..");
		}
		IngredientsItem ingredientsItem =optinalIngredientsItem.get();
		ingredientsItem.setStock(!ingredientsItem.isStock());
		return ingredientItemRepository.save(ingredientsItem);
	}

}

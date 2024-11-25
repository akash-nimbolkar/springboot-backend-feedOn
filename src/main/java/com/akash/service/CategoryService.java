package com.akash.service;

import java.util.List;

import com.akash.model.Category;

public interface CategoryService {
	
	
	public Category createCategory(String name, Long userId) throws Exception;
	//we use userid because using it we can find restaurant 
	//for which you want to create category
	
	public List<Category> findCategoryByRestaurantId(Long id) throws Exception;
	
	public Category findCategoryById(Long id) throws Exception;
	
	

}

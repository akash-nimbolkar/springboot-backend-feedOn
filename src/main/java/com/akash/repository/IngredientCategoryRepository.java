package com.akash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.model.IngredientsCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientsCategory, Long>{

	
	List<IngredientsCategory> findByRestaurantId(Long id);
}

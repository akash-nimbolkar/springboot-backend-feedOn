package com.akash.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akash.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {

//	List<Food> findByRestaurantId(Long restaurantId);
//	
//	@Query("SELECT f FROM Food f WHERE f.name LIKE %:keyword% OR f.foodCategory.name LIKE %:keyword%")
//	List<Food> searchFood(@Param("keyword") String keyword);
//	
	
	 List<Food> findByRestaurantId(Long restaurantId);

	    @Query("SELECT f FROM Food f LEFT JOIN FETCH f.foodCategory WHERE f.id = :id")
	    Optional<Food> findByIdWithCategory(@Param("id") Long id);

	    @Query("SELECT f FROM Food f LEFT JOIN FETCH f.foodCategory WHERE f.name LIKE %:keyword% OR f.foodCategory.name LIKE %:keyword%")
	    List<Food> searchFood(@Param("keyword") String keyword);
	
}

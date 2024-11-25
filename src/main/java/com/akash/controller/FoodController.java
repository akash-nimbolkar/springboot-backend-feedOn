package com.akash.controller;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.model.Food;
import com.akash.model.Restaurant;
import com.akash.model.User;
import com.akash.request.CreateFoodRequest;
import com.akash.service.FoodService;
import com.akash.service.RestaurantService;
import com.akash.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {
	
	@Autowired
	private FoodService foodService;

	@Autowired
	private UserService userService;

	@Autowired
	private RestaurantService restaurantService;

	@GetMapping("/search")
	public ResponseEntity<java.util.List<Food>> searhFood(@RequestParam String name,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		java.util.List<Food> foods = foodService.searchFood(name);
		return new ResponseEntity<>(foods, HttpStatus.OK);
	}
	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<java.util.List<Food>> getRestaurantFood(
			@RequestParam (required = false)boolean vegeterian,
			@RequestParam (required = false)boolean seasonal,
			@RequestParam (required = false)boolean nonveg,
			@RequestParam (required = false) String food_category,
			@PathVariable Long restaurantId,
			@RequestHeader("Authorization") String jwt) throws Exception {
		
		User user = userService.findUserByJwtToken(jwt);
		java.util.List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegeterian, nonveg, seasonal, food_category);
		return new ResponseEntity<>(foods, HttpStatus.OK);
	}


}

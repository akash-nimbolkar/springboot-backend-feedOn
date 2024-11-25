package com.akash.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.model.Category;
import com.akash.model.Food;
import com.akash.model.Restaurant;
import com.akash.repository.CategoryRepository;
import com.akash.repository.FoodRepository;
import com.akash.request.CreateFoodRequest;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Long categoryId, Restaurant restaurant) {
        Food food = new Food();
        Category fooCategory = categoryRepository.findById(req.getCategoryId()).orElseThrow(()-> new RuntimeException("CAtegory not found")) ;
        
        food.setFoodCategory(fooCategory);;
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegeterian(req.isVegeterian());

        Food saveFood = foodRepository.save(food);
        restaurant.getFoods().add(saveFood);

        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        foodRepository.deleteById(foodId);  // Deleting food entry
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegeterian, boolean isNonveg, boolean isSeasonal,
            String foodCategory) {
        
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegeterian && !isNonveg) {
            foods = filterByVegterian(foods, isVegeterian);
        } else if (!isVegeterian && isNonveg) {
            foods = filterByNonveg(foods, isNonveg);
        }

        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }

        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = filterByCategory(foods, foodCategory);
        }
        
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food ->
            food.getFoodCategory() != null && food.getFoodCategory().getName().equals(foodCategory)
        ).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> !food.isVegeterian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegterian(List<Food> foods, boolean isVegeterian) {
        return foods.stream().filter(food -> food.isVegeterian() == isVegeterian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findByIdWithCategory(foodId);
        return optionalFood.orElseThrow(() -> new Exception("Food not exist.."));
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}

package com.akash.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.model.Cart;
import com.akash.model.CartItem;
import com.akash.model.Food;
import com.akash.model.User;
import com.akash.repository.CartItemRepo;
import com.akash.repository.CartRepository;
import com.akash.request.AddCartItemRequest;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        // Check if the item already exists in the cart
        Optional<CartItem> existingItemOptional = cart.getItem().stream()
                .filter(cartItem -> cartItem.getFood().equals(food))
                .findFirst();

        if (existingItemOptional.isPresent()) {
            // Update quantity if the item is already in the cart
            CartItem existingItem = existingItemOptional.get();
            int newQuantity = existingItem.getQuantity() + req.getQuantity();
            return updateCartItemQuantity(existingItem.getId(), newQuantity);
        } else {
            // Create a new cart item if it doesn't exist
            CartItem newCartItem = new CartItem();
            newCartItem.setFood(food);
            newCartItem.setCart(cart);
            newCartItem.setQuantity(req.getQuantity());
            newCartItem.setIngredients(req.getIngredients());
            newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

            CartItem savedCartItem = cartItemRepo.save(newCartItem);
            cart.getItem().add(savedCartItem);
            cartRepository.save(cart);  // Save the cart after adding the new item

            return savedCartItem;
        }
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new Exception("Cart item not found"));

        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);

        return cartItemRepo.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new Exception("Cart item not found"));

        cart.getItem().remove(item);
        cartItemRepo.delete(item);  // Remove the item from the repository
        cart.setTotal(calculateCartTotals(cart));  // Update the cart's total
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) {
        Long total = 0L;
        for (CartItem cartItem : cart.getItem()) {
            total += cartItem.getTotalPrice();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        return cartRepository.findById(id)
                .orElseThrow(() -> new Exception("Cart not found"));
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        if (cart == null) {
            throw new Exception("Cart not found");
        }
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getItem().clear();
        cart.setTotal(0L);  // Set total to zero after clearing
        return cartRepository.save(cart);
    }
}

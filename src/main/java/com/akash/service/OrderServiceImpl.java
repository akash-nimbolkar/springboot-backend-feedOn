package com.akash.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.model.Address;
import com.akash.model.Cart;
import com.akash.model.CartItem;
import com.akash.model.Order;
import com.akash.model.OrderItem;
import com.akash.model.Restaurant;
import com.akash.model.User;
import com.akash.repository.AddressRepository;
import com.akash.repository.OrderItemRepo;
import com.akash.repository.OrderRepository;
import com.akash.repository.UserRepository;
import com.akash.request.OrderRequest;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        // Ensure that the restaurant ID is provided
       

        // Fetch the restaurant by restaurantId
       // Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());

        Address shippingAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
        if (order.getRestaurantId() == null) {
            throw new IllegalArgumentException("Restaurant ID must not be null");
        }
        Order createOrder = new Order();
        createOrder.setCustomer(user);
        createOrder.setCreatedAt(new Date());
        createOrder.setOrderStatus("PENDING");
        createOrder.setDeliveryAddress(savedAddress);
        createOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItem()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepo.save(orderItem);

            orderItems.add(savedOrderItem);
        }
        Long totalPrice = cartService.calculateCartTotals(cart);

        createOrder.setItems(orderItems);
        createOrder.setTotalPrice(totalPrice);

        Order saveOrder = orderRepository.save(createOrder);
        restaurant.getOrders().add(saveOrder);
        return createOrder;
    }


    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT FOR DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("PENDING") || orderStatus.equals("COMPLETED")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }

        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            throw new Exception("Order not found");
        }
        return optionalOrder.get();
    }
}

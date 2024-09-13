package com.example.service;


import com.example.exceptions.EmptyCartException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.*;
import com.example.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepo;
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

  
  @Transactional
  public ResponseEntity<?> placeOrder(int userId) {
      try {
    	  
      
      User user = userRepo.findById(userId);
      if (user == null) {
    	  throw new UserNotFoundException("Invalid User ID");
      }
      List<Cart> cartItems = cartRepo.findByUser_UserId(userId);
      if (cartItems.isEmpty()) {
    	  throw new EmptyCartException("Please add some items in cart! Cart is Empty");
      }

      double totalAmount = 0;

          for (Cart cart : cartItems) {
              Product product = cart.getProduct();
              product.getInventory().setQuantity(product.getInventory().getQuantity() - cart.getQuantity());
              totalAmount += product.getPrice() * cart.getQuantity();
              productRepo.save(product);
          }
          Order order = new Order();
          order.setUser(user);
          order.setTotalAmount(totalAmount);

          Order savedOrder = orderRepo.save(order);

          for (Cart cart : cartItems) {
              OrderItem orderItem = new OrderItem();
              orderItem.setOrder(savedOrder);
              orderItem.setProduct(cart.getProduct());
              orderItem.setQuantity(cart.getQuantity());
              orderItem.setPrice(cart.getProduct().getPrice());
              orderItemRepo.save(orderItem);
          }
          cartRepo.deleteAll(cartItems);
          return ResponseEntity.ok("Order placed successfully with total amount: " + totalAmount);
      }
      catch(ObjectOptimisticLockingFailureException e) {
          return ResponseEntity.ok("Failed to place order.Please try again.");
      }

  }
}


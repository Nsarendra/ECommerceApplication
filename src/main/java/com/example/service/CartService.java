

package com.example.service;

import com.example.DTO.CartDto;
import com.example.DTO.ProductDto;
import com.example.models.Cart;
import com.example.models.Product;
import com.example.models.User;
import com.example.repositories.CartRepository;
import com.example.repositories.ProductRepository;
import com.example.repositories.UserRepository;
import com.example.exceptions.ProductNotFoundException;
import com.example.exceptions.UserNotFoundException;
import com.example.exceptions.EmptyCartException;
import com.example.exceptions.InsufficientStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepo;
    
    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private UserRepository userRepo;

    @Transactional
    public ResponseEntity<String> addOrUpdateInCart(CartDto cart) {
   
        User user = userRepo.findById(cart.getUserId());
        		if (user == null) {
        		    throw new UserNotFoundException("Invalid User ID");
        		}

        Product product = productRepo.findById(cart.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Invalid Product ID"));

        if (product.getInventory().getQuantity() < cart.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for product ID " + cart.getProductId());
        }

        Optional<Cart> existingCartItem = cartRepo.findByUser_UserIdAndProduct_Id(cart.getUserId(), cart.getProductId());
        Cart cartItem;
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cart.getQuantity());
        } else {
            cartItem = new Cart();
            cartItem.setQuantity(cart.getQuantity());
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }
        cartRepo.saveAndFlush(cartItem);
        
        return ResponseEntity.ok("Successfully added item with productId: " + product.getId());
    }

    @Transactional
    public ResponseEntity<String> deleteCartItem(int productId, int userId) {
        Cart cartItem = cartRepo.findByUser_UserIdAndProduct_Id(userId, productId)
                .orElseThrow(() -> new ProductNotFoundException("Cart item not found for user ID " + userId + " and product ID " + productId));

        cartRepo.delete(cartItem);
        return ResponseEntity.ok("Cart item deleted successfully");
    }

    public List<ProductDto> getInCart(int userId) {
 
        List<Cart> cartItems = cartRepo.findByUser_UserId(userId);
        if(cartItems.isEmpty()) {
        	throw new EmptyCartException("Please add some items in cart! Cart is Empty");
        }
        List<ProductDto> productDTOs = new ArrayList<>();

        for (Cart cartItem : cartItems) {
            Product product = cartItem.getProduct();
            ProductDto productDTO = new ProductDto(product.getId(), product.getName(), product.getPrice());
            productDTOs.add(productDTO);
        }

        return productDTOs;
    }
}


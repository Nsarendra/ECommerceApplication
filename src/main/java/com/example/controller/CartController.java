package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.CartDto;
import com.example.DTO.ProductDto;
import com.example.models.Cart;
import com.example.models.Product;
import com.example.models.User;
import com.example.service.CartService;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	@PostMapping("/addInCart")
	@PreAuthorize("hasAuthority('user')")
	   public ResponseEntity<?> addInCart(@RequestBody CartDto cart ){
		   
		   return cartService.addOrUpdateInCart(cart);
	   }
	
	@DeleteMapping("/deleteInCart/{productId}/{userId}")
	@PreAuthorize("hasAuthority('user')")
	   public ResponseEntity<?> deleteInCart(@PathVariable int productId,@PathVariable int userId ){
		   
		   return cartService.deleteCartItem(productId,userId);
	   }
	

	
	@GetMapping("/getCartStatus/{userId}")
	@PreAuthorize("hasAuthority('user')")
	public List<ProductDto> getInCart(@PathVariable int userId){
		   
		   return cartService.getInCart(userId);
	   }
	
	
	    

}

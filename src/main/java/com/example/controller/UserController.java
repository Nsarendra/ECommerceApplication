package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.User;
import com.example.service.UserService;


@RestController
public class UserController {
	
	@Autowired
    private UserService userService;
	
	 @PostMapping("/register")
	   public ResponseEntity<?> register(@RequestBody User user){
		   
		   return userService.register(user);
	   }
	    
	    @PostMapping("/login")
	  public ResponseEntity<?> login(@RequestBody User user){
		  return userService.login(user);
	  }

}

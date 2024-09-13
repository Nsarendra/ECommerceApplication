package com.example.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.models.User;
import com.example.repositories.UserRepository;
import com.example.config.JwtTokenProvider;
import com.example.exceptions.InvalidDetailsException;
import com.example.exceptions.UserNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public ResponseEntity<?> register(User user){
		
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new InvalidDetailsException("User already exists or try to login");
		}
		userRepository.saveAndFlush(user);	
		return ResponseEntity.ok("You are succesfully registered " );
		}
		
		public ResponseEntity<?> login(User user){
			if(userRepository.existsByEmail(user.getEmail())) {
				
				User u= userRepository.findByEmail(user.getEmail());
				Map <String, Object> response=new HashMap<>();
				if(user.getPassword().equals(u.getPassword())) {
					
					 User tokenUser = userRepository.findByEmail(user.getEmail());

		                JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
		                String token = jwtTokenProvider.generateToken(tokenUser);
		                System.out.println(token);
					response.put("message", "Login Succesfull");
					response.put("userId",u.getUserId() );
					response.put("userName",u.getFullName());
					response.put("role", u.getRole());
					response.put("userEmail", u.getEmail());
					response.put("token", token);
					return ResponseEntity.ok().body(response);
					
				}
				else {
					throw new InvalidDetailsException("Invalid Password");
				}
			}
			else {
				throw new InvalidDetailsException("Invalid Email or not signed up in this website");
			}
			
		}
}

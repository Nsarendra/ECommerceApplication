package com.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findById(int id);
    User findByEmail(String email);
    boolean existsByEmail(String email); 
    
    
}
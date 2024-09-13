package com.example.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import com.example.models.Product;
import com.example.models.User;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	Optional<Product> findByName(String name);
	
	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
   
}

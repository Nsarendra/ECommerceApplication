package com.example.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Cart;
import com.example.models.Product;
import com.example.models.User;

@Repository
public interface CartRepository extends  JpaRepository<Cart, Integer> {
	 List<Cart> findByUser_UserId(int userId);

	 Optional<Cart> findByUser_UserIdAndProduct_Id(int userId, int productId);
}

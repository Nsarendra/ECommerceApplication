package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Inventory;
import com.example.models.Product;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>{

}

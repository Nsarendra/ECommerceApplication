package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.DTO.ProductDto2;
import com.example.DTO.ProductWithQuantityDTO;
import com.example.exceptions.ProductNotFoundException;
import com.example.models.Inventory;
import com.example.models.Product;
import com.example.repositories.InventoryRepository;
import com.example.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private InventoryRepository inventoryRepo;

	@Transactional
	public ResponseEntity<?> addOrUpdateProduct(ProductDto2 productDTO) {
	    Optional<Product> existingProduct = productRepo.findByName(productDTO.getName());

	    if (existingProduct.isPresent()) {
	        Product productToUpdate = existingProduct.get();
	        Inventory inventory = productToUpdate.getInventory();

	        if (inventory != null) {
	            inventory.setQuantity(inventory.getQuantity() + productDTO.getQuantity());
	        } else {
	            inventory = new Inventory();
	            inventory.setProduct(productToUpdate);
	            inventory.setQuantity(productDTO.getQuantity());
	        }
	        inventoryRepo.saveAndFlush(inventory);

	        return ResponseEntity.ok("Product inventory updated successfully.");
	    } else {
	        Product newProduct = new Product();
	        newProduct.setName(productDTO.getName());
	        newProduct.setPrice(productDTO.getPrice());
	        newProduct.setImage(productDTO.getImage());
	        Inventory inventory = new Inventory();
	        inventory.setProduct(newProduct);
	        inventory.setQuantity(productDTO.getQuantity());

	        newProduct.setInventory(inventory);

	        productRepo.save(newProduct);

	        return ResponseEntity.ok("Product added successfully.");
	    }
	}
	
	@Transactional
	public ResponseEntity<?> deleteProduct(int productId) {
        if (productRepo.existsById(productId)) {
            productRepo.deleteById(productId);
            return ResponseEntity.ok("Product and related inventory successfully deleted.");
        } else {
        	throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }
    }
	
	public List<ProductWithQuantityDTO> getAllProducts(){
		 List<Product> products = productRepo.findAll();
	        List<ProductWithQuantityDTO> productWithQuantityDTOs = new ArrayList<>();
	        for (Product product : products) {
	            Inventory inventory = product.getInventory();
	            int quantity;
	             if(inventory==null) {
	            	 quantity=0;
	             }
	             else {
	            	 quantity=inventory.getQuantity();
	             }
	            ProductWithQuantityDTO dto = new ProductWithQuantityDTO(
	                product.getId(),
	                product.getName(),
	                product.getPrice(),
	                product.getImage(),
	                quantity
	            );
	            productWithQuantityDTOs.add(dto);
	        }

	        return productWithQuantityDTOs;
	}

public ResponseEntity<Page<Product>> searchProductsByName(String name,int page,int size) {
	
	Pageable pageable = PageRequest.of(page, size);
	Page<Product> products=productRepo.findByNameContainingIgnoreCase(name, pageable);
        return ResponseEntity.ok(products);
    }


    
}

package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.ProductDto2;
import com.example.DTO.ProductWithQuantityDTO;
import com.example.models.Product;
import com.example.repositories.ProductRepository;
import com.example.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductService  productService;
	
	@GetMapping("/getAllProducts")
	public List<ProductWithQuantityDTO> getAllProducts() {
	
		return productService.getAllProducts();

	}
	
	@PostMapping("addOrUpdateProduct")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> addOrUpdateProduct(@RequestBody ProductDto2 product){
		return productService.addOrUpdateProduct(product);
	}
	
	@DeleteMapping("deleteProduct/{productId}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> deleteProduct(@PathVariable int productId){
		return productService.deleteProduct(productId);
	}
	

	
	@GetMapping("/search-products")
	@PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam("name") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        
      return  productService.searchProductsByName(name, page,size);
    }
}

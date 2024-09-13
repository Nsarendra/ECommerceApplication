package com.example.controller;


import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder/{userId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> placeOrder(@PathVariable int userId) {
        
        return orderService.placeOrder(userId);
    }
}


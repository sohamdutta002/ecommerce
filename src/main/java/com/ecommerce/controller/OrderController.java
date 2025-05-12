package com.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService=orderService;
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long userId){
		try{
			OrderDTO placedOrder=orderService.placeOrder(userId);
			return new ResponseEntity<>(placedOrder,HttpStatus.CREATED);
		}
		catch(Exception e){			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
}

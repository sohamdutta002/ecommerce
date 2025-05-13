package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<OrderDTO>> getAllOrders(@PathVariable Long userId){
		try {
			List<OrderDTO> orders=orderService.getOrdersByUser(userId);
			return new ResponseEntity<>(orders,HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/status/{orderId}")
	public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId,@RequestParam String status){
		try {
			OrderDTO updatedStatus=orderService.updateOrderStatus(orderId, status);
			return new ResponseEntity<>(updatedStatus,HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/payment/{orderId}")
	public ResponseEntity<OrderDTO> updateOrderPaymentStatus(@PathVariable Long orderId,@RequestParam String payment){
		try {
			OrderDTO updatedPayment=orderService.updatePaymentStatus(orderId, payment);
			return new ResponseEntity<>(updatedPayment,HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
}

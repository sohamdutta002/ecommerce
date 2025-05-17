package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.service.OrderService;

@Controller
@RequestMapping("admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

	private OrderService orderService;
	
	AdminOrderController(OrderService orderService){
		this.orderService=orderService;
	}
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getAllOrders(){
		try {
			return ResponseEntity.ok(orderService.viewAllOrders());
		} catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/status/{orderId}")
	public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId,@RequestParam String status){
		try {
			return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
		}	catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/payment/{orderId}")
	public ResponseEntity<OrderDTO> updatePaymentStatus(@PathVariable Long orderId,@RequestParam String payment){
		try {
			return ResponseEntity.ok(orderService.updatePaymentStatus(orderId, payment));
		}	catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
}

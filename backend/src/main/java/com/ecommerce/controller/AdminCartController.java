package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.service.CartService;

@Controller
@RequestMapping("admin/carts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCartController {

	private CartService cartService;

	AdminCartController(CartService cartService){
		this.cartService=cartService;
	}
	
	@GetMapping
	public ResponseEntity<List<CartItemDTO>> getAllCarts(){
		try{
			return ResponseEntity.ok(cartService.viewAllCarts());
		}	catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
}

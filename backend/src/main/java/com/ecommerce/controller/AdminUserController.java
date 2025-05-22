package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;

@Controller
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		try{
			return ResponseEntity.ok(userService.getAllUsers());
		}	catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllAdmins(){
		try {
			return ResponseEntity.ok(userService.getAllUsers());
		}	catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerAdmin(@RequestBody User user){
		try {
			return new ResponseEntity<>(userService.registerAdmin(user),HttpStatus.CREATED);
		}	catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}

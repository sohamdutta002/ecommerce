package com.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.User;
import com.ecommerce.service.JwtService;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;

	public UserController(UserService userService,AuthenticationManager authenticationManager,JwtService jwtService) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtService=jwtService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		try {
			userService.registerUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
			return ResponseEntity.ok(jwtService.generateToken(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login failed: " + e.getMessage());
		}
	}

	@PutMapping("/update-user")
	public ResponseEntity<User> updateUserDetails(@RequestBody User updatedUser) {
		try {
			User user = userService.updateUserProfile(updatedUser.getId(), updatedUser);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long id) {
		UserDTO userDTO = userService.getUserProfile(id);
		return ResponseEntity.ok(userDTO);
	}

}

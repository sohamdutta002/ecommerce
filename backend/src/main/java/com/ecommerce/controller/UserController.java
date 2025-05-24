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

import com.ecommerce.dto.AuthResponse;
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
	public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) {
		try {
			userService.registerUser(user);
			System.out.println(user);
			return loginUser(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUser(@RequestBody User user) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
			String token=jwtService.generateToken(user);
			UserDTO obtainedUser=userService.getUserByEmail(user.getEmail());
			return ResponseEntity.ok(new AuthResponse(token,obtainedUser,user.getRole()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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

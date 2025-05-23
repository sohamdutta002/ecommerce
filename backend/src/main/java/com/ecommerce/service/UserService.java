package com.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderItemDTO;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

	private PasswordEncoder encoder;
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	public User registerUser(User newUser) {
		Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
		if (existingUser.isPresent()) {
			throw new RuntimeException("User with this email already exists");
		}
		newUser.setRole(Role.USER);
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}

	public String registerAdmin(User newUser) {
		Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
		if (existingUser.isPresent()) {
			throw new RuntimeException("Admin with this email already exists");
		}
		newUser.setRole(Role.ADMIN);
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		userRepository.save(newUser);
		return "Admin added successfully";
	}

	public boolean loginUser(String email, String password) {
		Optional<User> user = userRepository.findByEmail(email);
		password = encoder.encode(password);
		if (user.isPresent() && user.get().getPassword().equals(password)) {
			return true;
		}
		throw new RuntimeException("Email Or Password is invalid");
	}

	public User updateUserProfile(Long id, User updatedUser) {
		return userRepository.findById(id).map(user -> {
			if (updatedUser != null) {
				if (updatedUser.getName() != null) {
					user.setName(updatedUser.getName());
				}
				if (updatedUser.getEmail() != null) {
					user.setEmail(updatedUser.getEmail());
				}
				if (updatedUser.getPassword() != null) {
					user.setPassword(updatedUser.getPassword());
				}
				if (updatedUser.getShippingAddress() != null) {
					user.setShippingAddress(updatedUser.getShippingAddress());
				}
				if (updatedUser.getPaymentDetails() != null) {
					user.setPaymentDetails(updatedUser.getPaymentDetails());
				}
			}
			return userRepository.save(user);
		}).orElseThrow(() -> new RuntimeException("User not found"));
	}

	public UserDTO getUserProfile(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		List<CartItemDTO> cartItems = user.getCartItems().stream()
				.map(item -> new CartItemDTO(item.getProduct().getProductId(), item.getProduct().getName(),
						item.getProduct().getPrice(), item.getQuantity(), item.getTotalPrice()))
				.collect(Collectors.toList());
		List<OrderDTO> orders = user.getOrders().stream().map(item -> new OrderDTO(item.getOrderId(),
				item.getOrderDate(), item.getTotalPrice(), item.getOrderStatus(), item.getShippingAddress(),
				item.getPaymentStatus(),
				item.getOrderItems().stream()
						.map(oi -> new OrderItemDTO(oi.getOrderItemId(), oi.getProductName(), oi.getProductPrice(),
								oi.getQuantity(), oi.getTotalPrice()))
						.collect(Collectors.toList())))
				.collect(Collectors.toList());
		return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole(),
				user.getShippingAddress(), user.getPaymentDetails(), cartItems, orders);
	}

	public UserDTO getUserByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		List<CartItemDTO> cartItems = user.getCartItems().stream()
				.map(item -> new CartItemDTO(item.getProduct().getProductId(), item.getProduct().getName(),
						item.getProduct().getPrice(), item.getQuantity(), item.getTotalPrice()))
				.collect(Collectors.toList());
		List<OrderDTO> orders = user.getOrders().stream().map(item -> new OrderDTO(item.getOrderId(),
				item.getOrderDate(), item.getTotalPrice(), item.getOrderStatus(), item.getShippingAddress(),
				item.getPaymentStatus(),
				item.getOrderItems().stream()
						.map(oi -> new OrderItemDTO(oi.getOrderItemId(), oi.getProductName(), oi.getProductPrice(),
								oi.getQuantity(), oi.getTotalPrice()))
						.collect(Collectors.toList())))
				.collect(Collectors.toList());
		return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole(),
				user.getShippingAddress(), user.getPaymentDetails(), cartItems, orders);
	}

	public List<UserDTO> getAllUsers() {
		List<UserDTO> users = userRepository.findAll().stream().filter(user -> user.getRole().equals(Role.USER))
				.map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
						user.getRole(), user.getShippingAddress(), user.getPaymentDetails(), null, null))
				.collect(Collectors.toList());

		return users;

	}

	public List<UserDTO> getAllAdmins() {
		List<UserDTO> admins = userRepository.findAll().stream().filter(user -> user.getRole().equals(Role.ADMIN))
				.map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
						user.getRole(), null, null, null, null))
				.collect(Collectors.toList());
		return admins;
	}

}

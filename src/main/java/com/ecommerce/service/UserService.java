package com.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User registerUser(User newUser) {
		Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
		if (existingUser.isPresent()) {
			throw new RuntimeException("User with this email already exists");
		}
		return userRepository.save(newUser);
	}

	public boolean loginUser(String email, String password) {
		Optional<User> user = userRepository.findByEmail(email);
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
		}).orElseThrow(()->new RuntimeException("User not found"));
	}

	public Optional<User> getUserProfile(Long id) {
		return userRepository.findById(id);
	}

}

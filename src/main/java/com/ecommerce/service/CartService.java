package com.ecommerce.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class CartService {
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public CartService(CartItemRepository cartItemRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	public CartItem addToCart(Long userId, Long productId, int quantity) {
		User user = userRepository.findById(userId).orElseThrow();
		Product product = productRepository.findById(productId).orElseThrow();
		CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product).orElse(new CartItem());

		cartItem.setProduct(product);
		cartItem.setUser(user);
		cartItem.setQuantity(cartItem.getQuantity() + quantity);
		return cartItemRepository.save(cartItem);
	}
	
	public CartItem updateCartItem(Long userId,Long productId,int quantity) {
		User user = userRepository.findById(userId).orElseThrow();
		Product product = productRepository.findById(productId).orElseThrow();
		CartItem cartItem=cartItemRepository.findByUserAndProduct(user, product).orElseThrow();
		cartItem.setQuantity(quantity);
		return cartItemRepository.save(cartItem);
	}

	public void removeFromCart(Long userId, Long productId) {
		User user = userRepository.findById(userId).orElseThrow();
		Product product = productRepository.findById(productId).orElseThrow();
		CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
				.orElseThrow(() -> new RuntimeException("Item not found in the cart"));

		cartItemRepository.delete(cartItem);
	}

	public Map<String, Object> viewCart(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		double total = cartItems.stream().mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity()).sum();

		Map<String, Object> response = new HashMap<>();
		response.put("items", cartItems);
		response.put("totalPrice", total);
		return response;
	}
}

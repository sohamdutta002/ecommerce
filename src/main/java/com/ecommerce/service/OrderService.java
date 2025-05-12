package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final CartItemRepository cartItemRepository;

	public OrderService(OrderRepository orderRepository, UserRepository userRepository,
			CartItemRepository cartItemRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.cartItemRepository = cartItemRepository;
	}

	public OrderDTO placeOrder(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}
		double total = cartItems.stream().mapToDouble(item -> item.getTotalPrice()).sum();

		Order order = new Order();
		order.setUser(user);
		order.setOrderItems(new ArrayList<>(cartItems));
		order.setTotalPrice(total);
		order.setShippingAddress(user.getShippingAddress());
		order.setOrderStatus("Pending");
		order.setPaymentStatus("Pending");
		Order savedOrder = orderRepository.save(order);
		cartItemRepository.deleteAll(cartItems);
		return convertToOrderDTO(savedOrder);
	}

	public OrderDTO convertToOrderDTO(Order order) {
		List<CartItemDTO> cartItemDTOs = order.getOrderItems().stream()
				.map(cartItem -> new CartItemDTO(cartItem.getCartItemId(), cartItem.getUser().getUserId(),
						cartItem.getProduct().getProductId(), cartItem.getProduct().getName(), cartItem.getQuantity(),
						cartItem.getTotalPrice()))
				.collect(Collectors.toList());
		return new OrderDTO(order.getOrderId(), order.getOrderDate(), order.getTotalPrice(), order.getShippingAddress(),
				order.getOrderStatus(), order.getPaymentStatus(), order.getUser().getUserId(),
				order.getUser().getName(), cartItemDTOs);
	}

	public List<OrderDTO> getOrdersByUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		List<Order> orders = orderRepository.findByUser(user);

		return orders.stream().map(this::convertToOrderDTO).collect(Collectors.toList());
	}

	public OrderDTO updateOrderStatus(Long orderId, String status) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderStatus(status);
		return convertToOrderDTO(orderRepository.save(order));
	}

	public OrderDTO updatePaymentStatus(Long orderId, String status) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		return convertToOrderDTO(orderRepository.save(order));
	}
}

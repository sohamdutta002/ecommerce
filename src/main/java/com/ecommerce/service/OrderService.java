package com.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderItemDTO;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
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
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		Order order = new Order();
		order.setUser(user);

		List<OrderItem> orderItems = cartItems.stream().map(ci -> {
			OrderItem oi = new OrderItem();
			oi.setProduct(ci.getProduct());
			oi.setProductName(ci.getProduct().getName());
			oi.setProductPrice(ci.getProduct().getPrice());
			oi.setQuantity(ci.getQuantity());
			oi.setTotalPrice(ci.getTotalPrice());
			oi.setOrder(order);
			return oi;
		}).collect(Collectors.toList());

		order.setOrderItems(orderItems);
		order.setTotalPrice(orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum());
		order.setShippingAddress(user.getShippingAddress());
		order.setOrderStatus("Pending");
		order.setPaymentStatus("UNPAID");
		Order savedOrder = orderRepository.save(order);
		cartItemRepository.deleteAll(cartItems);
		return convertToOrderDTO(savedOrder);
	}

	public OrderDTO convertToOrderDTO(Order order) {
		List<OrderItemDTO> item=order.getOrderItems().stream().map(oi->new OrderItemDTO(
					oi.getOrderItemId(),oi.getProductName(),oi.getProductPrice(),oi.getQuantity(),oi.getTotalPrice()
				)).collect(Collectors.toList());
		return new OrderDTO(order.getOrderId(), order.getOrderDate(), order.getTotalPrice(),
				order.getOrderStatus(), order.getShippingAddress(), order.getPaymentStatus(),
				item);
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
		order.setPaymentStatus(status);
		return convertToOrderDTO(orderRepository.save(order));
	}
}

package com.ecommerce.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private Long userId;
	private String name;
	private String email;
	private String password;
	private String shippingAddress;
	private String paymentDetails;
	private List<CartItemDTO> cartItems;
	private List<OrderDTO> orders;
}

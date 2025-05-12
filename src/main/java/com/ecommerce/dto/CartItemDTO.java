package com.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDTO {
	private Long cartItemId;
	private Long userId;
	private Long productId;
	private String productName;
	private int quantity;
	private double totalPrice;
	public CartItemDTO(Long cartItemId, Long userId, Long productId, String productName, int quantity,
			double totalPrice) {
		this.cartItemId = cartItemId;
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
}

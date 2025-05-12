package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDTO {
	private Long cartItemId;
	private Long userId;
	private Long productId;
	private String productName;
	private int quantity;
	private double total_price;
}

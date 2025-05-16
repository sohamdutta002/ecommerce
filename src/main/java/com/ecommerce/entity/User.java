package com.ecommerce.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String shippingAddress;
	private String paymentDetails;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<CartItem> cartItems=new ArrayList<>();
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private List<Order> orders=new ArrayList<>();
}

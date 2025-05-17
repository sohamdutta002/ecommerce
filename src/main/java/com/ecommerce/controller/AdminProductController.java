package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('Admin')")
public class AdminProductController {

	private ProductService productService;
	
	AdminProductController(ProductService productService){
		this.productService=productService; 
	}

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		try{
			return ResponseEntity.ok(productService.getAllProducts());
		}	catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> createProduct(@RequestBody Product product) {
		try{
			productService.addProduct(product);
			return ResponseEntity.ok("Product added");
		}	catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		try{
			System.out.println(product.getProductId());
			return ResponseEntity.ok(productService.updateProduct(product.getProductId(), product));
		}	catch(Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		try {
			productService.deleteProduct(id);
			return ResponseEntity.ok("Product deleted");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body("Product not deleted: "+e.getMessage());
		}
	}
}

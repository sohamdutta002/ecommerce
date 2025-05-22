package com.ecommerce.service;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final long EXPIRATION_TIME = 1000 * 60 * 60;
	private String SECRET_KEY;

	JwtService() {
		SECRET_KEY = generateSecretKey();
	}

	public String generateSecretKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
			keyGenerator.init(256);
			SecretKey secretKey = keyGenerator.generateKey();
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		} catch (Exception e) {
			throw new RuntimeException("Failed to generate secret key ", e);
		}
	}

	public String generateToken(User user) {

		return Jwts.builder().subject(user.getEmail()).claim("role", user.getRole().name()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(getSigningKey()).compact();
	}

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String extractUserName(String token) {

		return extractClaim(token,Claims::getSubject);
	}
	
	private	<T> T extractClaim(String token, Function<Claims,T> claimResolver) {
		final Claims claims=extractAllClaims(token);
		System.out.println(claims);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {

		final String userName=extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration); 
	}

}

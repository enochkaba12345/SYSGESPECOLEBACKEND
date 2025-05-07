package com.sysgepecole.demo.Security;

import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private Long jwtExpiration;
	
	public String generateJwtToken(Authentication athentication) {
		UserSecurityModel userpricipal = (UserSecurityModel) athentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject(userpricipal.getUsername())
				.claim("role", userpricipal.getAuthorities().stream()
	            .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public boolean validateJwtToken(String token) {
		
	     if (token == null || token.isEmpty()) {
	         logger.error("JWT String argument cannot be null or empty.");
	         return false;
	     }

	     try {
	         Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
	         return true;
	     } catch (SignatureException e) {
	         logger.error("Invalid JWT signature: {}", e.getMessage());
	     } catch (MalformedJwtException e) {
	         logger.error("Invalid JWT token: {}", e.getMessage());
	     } catch (ExpiredJwtException e) {
	         logger.error("JWT token is expired: {}", e.getMessage());
	     } catch (UnsupportedJwtException e) {
	         logger.error("JWT token is unsupported: {}", e.getMessage());
	     } catch (IllegalArgumentException e) {
	         logger.error("JWT claims string is empty: {}", e.getMessage());
	     } catch (JwtException e) {
	         logger.error("JWT validation error: {}", e.getMessage());
	     }

	     return false;
	 }

	
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	
	
}

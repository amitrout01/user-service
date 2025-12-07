package com.userservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

	private final Key signingKey;
	private final long expirationMs;

	public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-ms}") long expirationMs) {
		// SECRET must be sufficiently long for HS256 (>= 32 bytes).
		this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMs = expirationMs;
	}

	public String generateToken(String username, Map<String, Object> additionalClaims) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMs);

		JwtBuilder builder = Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiry)
				.signWith(signingKey, SignatureAlgorithm.HS256);

		if (additionalClaims != null && !additionalClaims.isEmpty()) {
			builder.addClaims(additionalClaims);
		}

		return builder.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}

	public String extractUsername(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
	}
}

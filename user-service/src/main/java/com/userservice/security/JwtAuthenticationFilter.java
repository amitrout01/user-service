package com.userservice.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		// Skip login/signup and public resources
		if (path.startsWith("/auth/") || path.equals("/") || path.equals("/index.html") || path.startsWith("/css/")
				|| path.startsWith("/js/") || path.startsWith("/images/")) {
			filterChain.doFilter(request, response);
			return;
		}

		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		final String token = header.substring(7);

		if (!jwtUtil.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		String username = jwtUtil.extractUsername(token);
		Claims claims = jwtUtil.extractAllClaims(token);

		Object rolesObj = claims.get("roles");
		List<SimpleGrantedAuthority> authorities = Collections.emptyList();
		if (rolesObj != null) {
			if (rolesObj instanceof String) {
				String rolesStr = (String) rolesObj;
				authorities = List.of(rolesStr.split(",")).stream().map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
			} else if (rolesObj instanceof java.util.List) {
				authorities = ((java.util.List<?>) rolesObj).stream().map(Object::toString)
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			}
		}

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		filterChain.doFilter(request, response);
	}

}

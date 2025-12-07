package com.userservice.service;

import com.userservice.model.Auth;
import com.userservice.model.User;
import com.userservice.repository.AuthRepository;
import com.userservice.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    public  JwtUtil jwtUtil;
    private AuthRepository authRepository;

    public AuthService(JwtUtil jwtUtil, AuthRepository authRepository) {
        this.jwtUtil = jwtUtil;
        this.authRepository = authRepository;
    }

    /**
     * Create JWT access token for given user.
     */
    public String createAccessToken(User user) {
        Map<String, Object> claims = Map.of(
                "roles",
                user.getRoles().stream()
                        .map(role -> role.getRoleName()) // extract role name
                        .toList()
        );
        return jwtUtil.generateToken(user.getUsername(), claims);
    }
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    // Optional: create and store refresh token in DB (example)
    public Auth createAndStoreRefreshToken(String username, String refreshToken, long expiresMs) {
        Auth auth = new Auth();
        auth.setUsername(username);
        auth.setToken(refreshToken);
        auth.setCreatedAt(new Date());
        auth.setExpiresAt(new Date(System.currentTimeMillis() + expiresMs));
        return authRepository.save(auth);
    }

    public Optional<Auth> findAuthByUsername(String username) {
        return authRepository.findByUsername(username);
    }
}

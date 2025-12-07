package com.userservice.loginController;

import com.userservice.constants.ErrorCode;
import com.userservice.model.Role;
import com.userservice.model.User;
import com.userservice.repository.RoleRepository;
import com.userservice.requestVo.LoginRequest;
import com.userservice.response.ApiResponse;
import com.userservice.service.AuthService;
import com.userservice.service.UserService;
import com.userservice.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	// ===== LOGIN  =====
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
		User user = userService.findByUsername(request.getUsername());

		if (user == null || !userService.checkPassword(request.getPassword(), user.getPassword())) {
			return ResponseEntity.status(401).body(ResponseUtil.error(ErrorCode.LOGIN_FAILED));
		}

		String token = authService.createAccessToken(user);
		return ResponseEntity.ok(ResponseUtil.success(token, user.getUsername()));
	}

	// ===== SIGNUP =====
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@Valid @RequestBody LoginRequest request) {

		if (userService.findByUsername(request.getUsername()) != null) {
			return ResponseEntity.badRequest().body(ResponseUtil.error(ErrorCode.USER_ALREADY_EXISTS));
		}

		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setName(request.getName());

		Role defaultRole = roleRepository.findByRoleName("ROLE_USER")
				.orElseThrow(() -> new RuntimeException(ErrorCode.DEFAULT_ROLE_NOT_FOUND.getMessage()));

		user.setRoles(Set.of(defaultRole));
		userService.createUser(user, request.getPassword());

		return ResponseEntity.ok(ResponseUtil.success(user.getUsername()));
	}

	// ===== TOKEN VALIDATION =====
	@GetMapping("/validate")
	public ResponseEntity<ApiResponse> validate(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(401).body(ResponseUtil.error(ErrorCode.MISSING_AUTH_HEADER));
		}

		String token = authHeader.substring(7);

		if (!authService.validateToken(token)) {
			return ResponseEntity.status(401).body(ResponseUtil.error(ErrorCode.TOKEN_INVALID));
		}

		String username = authService.jwtUtil.extractUsername(token);
		return ResponseEntity.ok(ResponseUtil.success(token, username));
	}
}

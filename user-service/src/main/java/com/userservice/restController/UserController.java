package com.userservice.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import com.userservice.requestVo.ProductRequestVo;
import com.userservice.response.ApiResponse;
import com.userservice.service.UserProductService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserProductService userProductService;

	@Autowired
	private ObjectMapper objectMapper;

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.ok(userRepository.save(user));
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return userRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
		return userRepository.findById(id).map(user -> {
//			user.setName(userDetails.getName());
//			user.setEmail(userDetails.getEmail());
			return ResponseEntity.ok(userRepository.save(user));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		return userRepository.findById(id).map(user -> {
			userRepository.delete(user);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/product/{productId}")
	public ApiResponse getProduct(@PathVariable Long productId) {
		try {
			ProductRequestVo product = userProductService.getProductDetails(productId);
			String data = objectMapper.writeValueAsString(product); // Convert Product to JSON string
			return new ApiResponse(data, "Product fetched successfully", null, "200");
		} catch (Exception e) {
			return new ApiResponse(null, "Error fetching product: " + e.getMessage(), null, "500");
		}
	}

}

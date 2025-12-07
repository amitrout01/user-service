package com.userservice.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.userservice.response.ApiResponse;
import com.userservice.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user/product")
public class ProductRestController {

	private static final String PRODUCT_SERVICE_URL = "http://localhost:8082/product/api/products/getAllProducts";

	private final ObjectMapper objectMapper;

	public ProductRestController() {
		this.objectMapper = new ObjectMapper();

		// 1️⃣ Register Java 8 time module
		this.objectMapper.registerModule(new JavaTimeModule());

		// 2️⃣ Disable timestamps for proper date formatting
		this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// 3️⃣ Optional: set global date format
		this.objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	@GetMapping("/productList")
	public ApiResponse getProductsFromProductService() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String responseString = restTemplate.getForObject(PRODUCT_SERVICE_URL, String.class);

			// Convert to ApiResponse
			ApiResponse apiResponse = objectMapper.readValue(responseString, ApiResponse.class);

			List<ProductResponse> products = List.of();

			if (apiResponse != null && apiResponse.getData() != null) {
				// Cast ApiResponse data into List<ProductResponse>
				products = objectMapper.convertValue(apiResponse.getData(),
						objectMapper.getTypeFactory().constructCollectionType(List.class, ProductResponse.class));
			}

			ApiResponse response = new ApiResponse();
			response.setStatus("success");
			response.setMessage("Products fetched successfully");
			response.setData(products);
			response.setFilePath(null);

			return response;

		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse errorResponse = new ApiResponse();
			errorResponse.setStatus("error");
			errorResponse.setMessage("Failed to fetch products from product-service: " + e.getMessage());
			errorResponse.setData(null);
			errorResponse.setFilePath(null);
			return errorResponse;
		}

	}
}
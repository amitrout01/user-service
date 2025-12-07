package com.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.requestVo.ProductRequestVo;

@Service
public class UserProductService {

	@Autowired
	private RestTemplate restTemplate;

	public ProductRequestVo getProductDetails(Long productId) {
		// Use Eureka service name "product-service" to call
		String urlFields = "http://product-service/products/" + productId;
		return restTemplate.getForObject(urlFields, ProductRequestVo.class);
	}

}

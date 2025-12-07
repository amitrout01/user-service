package com.userservice.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductResponse {

	private Long id;
	private String name;
	private String description;
	private String category;
	private String subCategory;
	private BigDecimal price;
	private int quantity;
	private String brand;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	public ProductResponse() {
	}

	public ProductResponse(Long id, String name, String description, String category, String subCategory,
			BigDecimal price, int quantity, String brand, LocalDateTime createdDate, LocalDateTime updatedDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.subCategory = subCategory;
		this.price = price;
		this.quantity = quantity;
		this.brand = brand;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "ProductResponse [id=" + id + ", name=" + name + ", description=" + description + ", category="
				+ category + ", subCategory=" + subCategory + ", price=" + price + ", quantity=" + quantity + ", brand="
				+ brand + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}

}

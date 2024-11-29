package com.day.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProductDto {

	@NotEmpty(message = "The name is required.")
	private String name;
	@NotEmpty(message = "The brand is required.")
	private String brand;
	@Size(min = 10, message = "The description should be al least 10 characters")
	@Size(max = 100, message = "The description cannot exceed 100 characters")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

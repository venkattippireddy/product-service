package com.productservice.exceptions;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2947390810766887567L;

	public ProductNotFoundException(String message) {
		super(message);
	}
}

package com.productservice.exceptions;

/**
 * The ProductNotFoundException class used if product not found it can throw.
 *
 * @author Tippireddy
 * @since 26-Feb-2019
 */
public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2947390810766887567L;

	public ProductNotFoundException(String message) {
		super(message);
	}
}

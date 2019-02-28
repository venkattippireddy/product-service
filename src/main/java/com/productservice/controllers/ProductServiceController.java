package com.productservice.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.productservice.dao.entity.Product;
import com.productservice.exceptions.ProductNotFoundException;
import com.productservice.service.ProductServices;

/**
 * The ProductServiceController class provide rest controller service for given
 * request.
 *
 * @author Tippireddy
 * @since 26-Feb-2019
 */
@RestController
public class ProductServiceController {

	@Autowired
	private ProductServices productService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = productService.getProducts();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
		Optional<Product> product = productService.getProduct(productId);
		return ResponseEntity.ok(product.orElseThrow(
				() -> new ProductNotFoundException("No Product found with given Informication " + productId)));
	}

	@PostMapping("/products")
	public ResponseEntity<Product> save(@RequestBody Product product) {
		return ResponseEntity.ok(productService.saveorUpdate(product));
	}

	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long productId) {
		Optional<Product> getProduct = productService.getProduct(productId);
		if (!getProduct.isPresent())
			return ResponseEntity.ok(getProduct.orElseThrow(
					() -> new ProductNotFoundException("No Product found with given Informication " + productId)));
		product.setId(productId);
		return ResponseEntity.ok(productService.saveorUpdate(product));
	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<?> delete(@PathVariable Long productId) {
		boolean isExist = productService.delete(productId);
		if (!isExist)
			return ResponseEntity.ok("No Product found with given Informication " + productId);
		return ResponseEntity.ok("Product deleted sucessfully");
	}

}

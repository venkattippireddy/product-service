package com.productservice.controllers;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.productservice.ProductReviewServiceProxy;
import com.productservice.dao.entity.Product;
import com.productservice.dao.entity.ProductReview;
import com.productservice.exceptions.ProductNotFoundException;
import com.productservice.service.ProductReviewService;
import com.productservice.service.ProductServices;

@RestController
public class ProductServiceController {

	@Autowired
	private ProductServices productService;

	@Autowired
	private ProductReviewService productReviewService;

	@Autowired
	private ProductReviewServiceProxy productReviewServiceProxy;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = productService.getProducts();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Resource<Product>> getProduct(@PathVariable Long productId) {
		return productService.getProduct(productId).map(product -> {
			product.setReviews(productReviewServiceProxy.getProductReviews("", productId));
			Resource<Product> resource = new Resource<Product>(product);
			ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getProducts());
			resource.add(linkTo.withRel("all-products"));
			return ResponseEntity.ok(resource);
		}).orElseThrow(() -> new ProductNotFoundException("id-" + productId));
	}

	@PostMapping("/products/{productId}/reviews")
	public ResponseEntity<ProductReview> saveReviews(@RequestBody ProductReview review, @PathVariable int productId) {
		productReviewService.saveProductReview(productId, review);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}")
				.buildAndExpand(review.getId()).toUri();
		return ResponseEntity.created(location).body(review);
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

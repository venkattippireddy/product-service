package com.productservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.productservice.dao.entity.ProductReview;


@FeignClient(name = "product-review", fallback=FallBackProductReviews.class)
public interface ProductReviewServiceProxy {

	@GetMapping("/products/{productId}/reviews")
	public List<ProductReview> getProductReviews(@RequestHeader(value = "SharedSecret") String secret,
			@PathVariable("productId") Long productId);
}

	@Component
	class FallBackProductReviews implements ProductReviewServiceProxy {

		public List<ProductReview> getProductReviews(String secret, Long prodId) {
			List<ProductReview> reviewList = new ArrayList<>();
			reviewList.add(new ProductReview());
			return reviewList;
		}
	}



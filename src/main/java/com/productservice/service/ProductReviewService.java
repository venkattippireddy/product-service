package com.productservice.service;

import java.util.List;

import com.productservice.dao.entity.ProductReview;

public interface ProductReviewService {

	public List<ProductReview> getProductReviews(Long id);

	public ProductReview saveProductReview(long id, ProductReview review);

}

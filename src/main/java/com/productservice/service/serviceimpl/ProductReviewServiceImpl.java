package com.productservice.service.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.productservice.dao.entity.ProductReview;
import com.productservice.service.ProductReviewService;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${product.review.url}")
	private String review_url;

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * Getting Product review details From Product Review service.
	 */
	@Override
	public List<ProductReview> getProductReviews(Long productId) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("SharedSecret", "Basic");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<List<ProductReview>> response = restTemplate.exchange(
				review_url.replace("http://localhost:8086", serviceInstancesByApplicationName("PRODUCT-REVIEW")),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<ProductReview>>() {
				}, productId);

		List<ProductReview> reviews = response.getBody();
		return reviews;
	}

	/**
	 * Product Review Service to Save.
	 */
	@Override
	public ProductReview saveProductReview(long prodId, ProductReview review) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("API-KEY", "Basic");
		HttpEntity<ProductReview> request = new HttpEntity<ProductReview>(review, headers);
		ProductReview reviews = restTemplate.postForObject(review_url, request, ProductReview.class, prodId);

		return reviews;
	}

	/**
	 * Reading end point from eureka server to consume product review service
	 * 
	 * @param applicationName
	 * @return
	 */
	private String serviceInstancesByApplicationName(String applicationName) {
		return this.discoveryClient.getInstances(applicationName).get(0).getUri().toString();
	}

}

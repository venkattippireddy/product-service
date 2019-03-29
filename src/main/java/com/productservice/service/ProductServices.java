package com.productservice.service;

import java.util.List;
import java.util.Optional;

import com.productservice.dao.entity.Product;

public interface ProductServices {

	public List<Product> getProducts();

	public Optional<Product> getProduct(Long productId);

	public Product saveorUpdate(Product product);

	public boolean delete(Long productId);

}

package com.productservice.service;

import java.util.List;
import java.util.Optional;

import com.productservice.dao.entity.Product;

/**
 * The ProductServices contract for  product service.
 *
 * @author Tippireddy
 * @since 26-Feb-2019
 */
public interface ProductServices {

	public List<Product> getProducts();

	public Optional<Product> getProduct(Long productId);

	public Product saveorUpdate(Product product);

	public boolean delete(Long productId);

}

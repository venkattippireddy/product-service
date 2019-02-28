package com.productservice.service.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productservice.dao.entity.Product;
import com.productservice.dao.repositorys.ProductRepository;
import com.productservice.service.ProductServices;

/**
 * The ProductServicesImp class provide services to controller.
 *
 * @author Tippireddy
 * @since 26-Feb-2019
 */
@Service
public class ProductServicesImp implements ProductServices {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getProducts() {
		return StreamSupport.stream(productRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Product saveorUpdate(Product product) {
		return productRepository.save(product);

	}

	@Override
	public Optional<Product> getProduct(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		return product;
	}

	@Override
	public boolean delete(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		boolean isExist = product.isPresent();
		if(isExist)
			productRepository.deleteById(productId);
		return isExist;
	}

}

package com.productservice.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.productservice.dao.entity.Product;
import com.productservice.dao.repositorys.ProductRepository;
import com.productservice.exceptions.ProductNotFoundException;
import com.productservice.service.serviceimpl.ProductServicesImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServicesImpl productService;

	static Product product1 = new Product();
	static Product product2 = new Product();

	@BeforeClass
	public static void setProducts() {
		product1.setId(new Long(1));
		product1.setName("Samsung HD");
		product1.setDescription("Default HD");
		product1.setPrice(32457.50);
		product1.setQuantity(5);
		product1.setVersion("1");

		product2.setId(new Long(2));
		product2.setName("Samsung Full HD");
		product2.setDescription("Full HD");
		product2.setPrice(39457.50);
		product2.setQuantity(4);
		product2.setVersion("1");

	}

	@Test
	public void getProducts() {
		List<Product> productList = new ArrayList<>();
		productList.add(product1);
		productList.add(product2);

		productList.stream().filter(prod -> prod.getName().equals("Samsung Full HD")).collect(Collectors.toList());
		when(productRepository.findAll()).thenReturn(productList);
		List<Product> prodList = productService.getProducts();
		assertEquals(productList, prodList);
	}

	@Test
	public void getProductById() {
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product1));
		Optional<Product> prod = productService.getProduct(1L);
		assertEquals(product1, prod.get());
	}

	@Test(expected = ProductNotFoundException.class)
	public void testProductNotFoundException() {
		when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
		Optional<Product> prod = productService.getProduct(1L);
		prod.orElseThrow(() -> new ProductNotFoundException("id-" + 1L));
	}

	@Test(expected = NoSuchElementException.class)
	public void testNoSuchElementException() {
		when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
		Optional<Product> prod = productService.getProduct(1L);
		prod.get();
	}

	@Test
	public void testSaveProduct() {
		when(productRepository.save(product1)).thenReturn(product1);
		Product prod = productService.saveorUpdate(product1);
		assertEquals(prod, product1);
	}

	@Test
	public void update() {
		when(productRepository.save(product1)).thenReturn(product1);
		Product updateProd = productService.saveorUpdate(product1);
		assertEquals(updateProd, product1);
	}

}

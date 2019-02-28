package com.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The ProductServicesApplication class is main base calss for spring boot.
 *
 * @author Tippireddy
 * @since 26-Feb-2019
 */
@SpringBootApplication
@EnableSwagger2
public class ProductServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServicesApplication.class, args);
	}

}

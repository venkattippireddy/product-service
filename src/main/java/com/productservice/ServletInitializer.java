package com.productservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The ServletInitializer class provide configure  Builder.
 *
 * @author Tippireddy
 * @since 26-Feb-2019
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProductServicesApplication.class);
	}

}

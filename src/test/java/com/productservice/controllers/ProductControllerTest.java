package com.productservice.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.productservice.controllers.ProductServiceController;
import com.productservice.dao.entity.Product;
import com.productservice.service.ProductServices;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductServiceController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private ProductServices productService;

	static Product mockProd1 = new Product();
	static Product mockProd2 = new Product();
	static Product mockProd3 = new Product();

	static List<Product> prodList = new ArrayList<>();
	static JSONObject mockProdJson;

	@BeforeClass
	public static void initialize() throws JSONException {

		mockProd1.setId(new Long(1));
		mockProd1.setName("Samsung HD");
		mockProd1.setDescription("Default HD");
		mockProd1.setPrice(32457.50);
		mockProd1.setQuantity(5);
		mockProd1.setVersion("1");

		mockProd2.setId(new Long(2));
		mockProd2.setName("Samsung Full HD");
		mockProd2.setDescription("Full HD");
		mockProd2.setPrice(39457.50);
		mockProd2.setQuantity(4);
		mockProd2.setVersion("1");

		mockProd3.setId(new Long(3));
		mockProd3.setName("Samsung DTH");
		mockProd3.setDescription("DTH");
		mockProd3.setPrice(36457.50);
		mockProd3.setQuantity(3);
		mockProd3.setVersion("1");

		prodList.add(mockProd1);

		mockProdJson = new JSONObject();

		mockProdJson.accumulate("id", 1);
		mockProdJson.accumulate("name", "Samsung HD");
		mockProdJson.accumulate("description", "Default HD");
		mockProdJson.accumulate("price", 32457.50);
		mockProdJson.accumulate("quantity", 5);
		mockProdJson.accumulate("version", "1");

	}

	@Test
	public void getProducts() throws Exception {

		Mockito.when(productService.getProducts()).thenReturn(prodList);

		JSONArray responce = new JSONArray();
		responce.put(mockProdJson);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockmvc.perform(requestBuilder).andReturn();

		JSONAssert.assertEquals(responce.toString(), result.getResponse().getContentAsString(), true);
	}

	@Test
	public void getProductById() throws Exception {

		Mockito.when(productService.getProduct((1L))).thenReturn(prodList.stream().findFirst());

		JSONArray responce = new JSONArray();
		responce.put(mockProdJson);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockmvc.perform(requestBuilder).andReturn();

		JSONAssert.assertEquals(responce.get(0).toString(), result.getResponse().getContentAsString(), true);
	}

	@Test
	public void saveProducts() throws Exception {

		Mockito.when(productService.saveorUpdate(Mockito.any())).thenReturn(mockProd2);

		JSONObject suceessResponse = new JSONObject();
		suceessResponse.accumulate("id", 2);
		suceessResponse.accumulate("name", "Samsung Full HD");
		suceessResponse.accumulate("description", "Full HD");
		suceessResponse.accumulate("price", 39457.5);
		suceessResponse.accumulate("quantity", 4);
		suceessResponse.accumulate("version", "1");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products").content(suceessResponse.toString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockmvc.perform(requestBuilder).andReturn();

		JSONAssert.assertEquals(suceessResponse.toString(), result.getResponse().getContentAsString(), true);
	}

	@Test
	public void updateProducts() throws Exception {

		Mockito.when(productService.saveorUpdate(mockProd1)).thenReturn(mockProd1);

		JSONObject suceessResponse = new JSONObject();
		suceessResponse.accumulate("id", 1);
		suceessResponse.accumulate("name", "Samsung HD");
		suceessResponse.accumulate("description", "Default HD");
		suceessResponse.accumulate("price", 32457.50);
		suceessResponse.accumulate("quantity", 5);
		suceessResponse.accumulate("version", "1");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/1")
				.content(suceessResponse.toString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockmvc.perform(requestBuilder).andReturn();

		assertEquals(suceessResponse.toString(), result.getResponse().getContentAsString());
	}

	@Test
	public void deleteProduct() throws Exception {

		Mockito.when(productService.delete(2L)).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/2");
		MvcResult result = mockmvc.perform(requestBuilder).andReturn();
		assertEquals("Product deleted sucessfully", result.getResponse().getContentAsString());
	}

}

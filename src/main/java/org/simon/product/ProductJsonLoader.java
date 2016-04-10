package org.simon.product;

import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Use Google Gson API to load product category info from json file
 * 
 * @author Simon
 *
 */
@Service
@EnableAutoConfiguration
public class ProductJsonLoader {
	/**
	 * load from Json file.
	 * 
	 * @return ProductCategory
	 */
	public ProductCategory loadJsonCategory() {

		Reader reader = new InputStreamReader(ProductJsonLoader.class.getResourceAsStream("/phones.json"));
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		ProductCategoryImp p = gson.fromJson(reader, ProductCategoryImp.class);
		return p;
	}
}

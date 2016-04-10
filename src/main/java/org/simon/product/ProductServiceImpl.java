package org.simon.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

/**
 * Use JsonLoader to load category from JSON file , then serve to web layer controller
 * 
 * @author Simon
 *
 */
@Service
@EnableAutoConfiguration
public class ProductServiceImpl implements ProductService {

	private ProductJsonLoader categoryLoader;

	@Autowired
	public ProductServiceImpl(ProductJsonLoader categoryLoader) {
		this.categoryLoader = categoryLoader;
	}

	@Override
	public ProductCategory getProductCategory(String id) {
		return this.categoryLoader.loadJsonCategory();
	}

}

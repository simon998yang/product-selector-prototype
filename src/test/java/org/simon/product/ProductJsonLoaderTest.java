package org.simon.product;

import junit.framework.TestCase;

public class ProductJsonLoaderTest extends TestCase {

	public void testLoadJsonCategory() {
		ProductJsonLoader loader = new ProductJsonLoader();
		ProductCategory category = loader.loadJsonCategory();
		assertEquals("must be 20 models:",category.getProducts().size(),20);
		assertEquals("first product id is motorola-xoom-with-wi-fi:", category.getProducts().get(0).getId(),"motorola-xoom-with-wi-fi");
		
	}

}

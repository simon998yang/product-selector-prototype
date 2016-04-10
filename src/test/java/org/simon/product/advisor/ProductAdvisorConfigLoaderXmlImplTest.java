package org.simon.product.advisor;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.simon.product.ProductCategory;
import org.simon.product.ProductJsonLoader;
import org.simon.product.advisor.config.ProductAdvisorFilter;
import org.simon.product.advisor.config.impl.ProductAdvisorConfigLoaderXmlImpl;
import org.simon.product.advisor.config.impl.ProductAdvisorConfigXmlImpl;

public class ProductAdvisorConfigLoaderXmlImplTest extends TestCase {

	private ProductAdvisorConfigLoaderXmlImpl loader;

	@Override
	protected void setUp() throws Exception {
		loader = new ProductAdvisorConfigLoaderXmlImpl();
		loader.setMappingPath("org/simon/product/advisor/HPProductAdvisorMapping.xml");
		loader.setFileIsInClassPath(true);
		loader.setAdvisorConfigPath("org/simon/product/advisor");
		super.setUp();
	}

	public void testLoad() throws Exception {
		ProductAdvisorConfigXmlImpl config = loader.load("org/simon/product/advisor/product-advisor.xml");
		Assert.assertEquals("advisor-1", config.getId());

		ProductAdvisorFilter filter = config.getFilterGroups().get(0).getFilters().get(0);

		ProductJsonLoader productLoader = new ProductJsonLoader();
		ProductCategory category = productLoader.loadJsonCategory();
		List candidateProducts = filter.getCandidateProducts(category, 300, 400);
		Assert.assertEquals(2, candidateProducts.size());
	}

}

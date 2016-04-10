package org.simon.product.advisor.config;

import java.util.List;

import org.simon.product.Product;
import org.simon.product.ProductCategory;

/**
 * POJO object for advisor configuration xml
 * 
 * @author Simon.Yang
 * 
 */
public interface ProductAdvisorFilter {

	public String getId();

	/**
	 * flexiable method to provide metadata for different displaystyle and type with single interface
	 * 
	 * @param key
	 * @return value for the key
	 */
	public String getMetaData(String key);

	/**
	 * the implement method will handle the detail how to get the validated SKUs for this particular Filter
	 * 
	 * 1. user will directly select all the SKUs which applicable to this filter in hamster 2. user will give price
	 * range in hamster for this filter, need to return all the validated SKUs accordingly
	 * 
	 * @return
	 */
	public List<Product> getCandidateProducts(ProductCategory category, Object min, Object max);

}

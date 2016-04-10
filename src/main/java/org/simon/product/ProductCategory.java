package org.simon.product;

import java.util.List;

/**
 * Interface for product category.
 * 
 * @author Simon
 *
 */
public interface ProductCategory {
	/**
	 * 
	 * @return category name
	 */
	String getProductCategoryName();

	/**
	 * 
	 * @return all products in the category
	 */
	List<Product> getProducts();

}

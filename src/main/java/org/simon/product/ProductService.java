package org.simon.product;

/**
 * Service to provide product category
 * 
 * @author Simon
 *
 */
public interface ProductService {
	/**
	 * 
	 * @param id
	 *            for category
	 * @return category
	 */
	ProductCategory getProductCategory(String id);

}

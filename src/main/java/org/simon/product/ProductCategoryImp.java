package org.simon.product;

import java.util.ArrayList;
import java.util.List;

import org.simon.product.Product;
import org.simon.product.ProductCategory;
import org.simon.product.ProductImp;

/**
 * Product category implementation, story all products in list.
 * 
 * @author Simon
 *
 */
public class ProductCategoryImp implements ProductCategory {

	/**
	 * List to store all products.
	 */
	private List<ProductImp> products = new ArrayList<ProductImp>();

	@Override
	public String getProductCategoryName() {

		return "Mobile Phones";
	}

	@Override
	public List getProducts() {
		return products;
	}

	/**
	 * Just set method.
	 * 
	 * @param products
	 *            products
	 */
	public void setProducts(List<ProductImp> products) {
		this.products = products;
	}

}

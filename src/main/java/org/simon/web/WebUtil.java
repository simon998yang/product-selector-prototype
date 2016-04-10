package org.simon.web;

import java.util.Iterator;
import java.util.List;

import org.simon.product.Product;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

@EnableAutoConfiguration
@Service
public class WebUtil {
	/**
	 * format product ids as string for front end
	 * 
	 * @param products
	 * @return product IDs as "id1,id2,id3,"
	 */
	public String getIDsAsString(List<Product> products) {
		String s = "";
		Iterator<Product> it = products.iterator();
		while (it.hasNext()) {
			s += ((Product) it.next()).getId() + ",";
		}
		return s;
	}
}

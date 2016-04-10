package org.simon.product.advisor;

import java.util.List;

import org.simon.product.ProductCategory;
import org.simon.product.ProductService;
import org.simon.product.advisor.config.ProductAdvisorConfig;
import org.simon.product.advisor.config.ProductAdvisorConfigLoader;
import org.simon.product.advisor.config.ProductAdvisorFilter;
import org.simon.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
@EnableAutoConfiguration
public class ProductFilterService {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductAdvisorConfigLoader configLoader;

	@Autowired
	private WebUtil webUtil;

	/**
	 * Select products from the category by applying rule of the filter based on the range parameter (minvalue,maxvalue)
	 * 
	 * @param category
	 * @param filterId
	 * @param range
	 * @return selected products' ids as string "id1,id2,id3"
	 */
	public String filter(ProductCategory category, String filterId, String range) {
		String[] strs = range.split(",");
		Double min = Double.parseDouble(strs[0]);
		Double max = Double.parseDouble(strs[1]);

		try {
			ProductAdvisorConfig config = configLoader.loadDefault();
			ProductAdvisorFilter filter = config.getFilter(filterId);
			List products = filter.getCandidateProducts(category, min, max);
			return webUtil.getIDsAsString(products);
		} catch (Exception e) {
			// TODO Handle exception properly
			e.printStackTrace();
		}
		return "";

	}
}

package org.simon.product.advisor.config;

import java.util.List;

/**
 * POJO object for advisor configuration xml
 * 
 * @author Simon.Yang
 * 
 */
public interface ProductAdvisorConfig {

	public String getId();

	public List<ProductAdvisorFilterGroup> getFilterGroups();

	public String getMetaData(String key);

	public ProductAdvisorFilter getFilter(String filterId);

}

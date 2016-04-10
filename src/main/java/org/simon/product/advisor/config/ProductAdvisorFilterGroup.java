package org.simon.product.advisor.config;

import java.util.List;

/**
 * 
 * @author Simon.Yang
 * 
 */
public interface ProductAdvisorFilterGroup {

	public String getId();

	public String getQuestionStyle();

	// public String getHelpDesc();

	// public String getHelpAssetID();

	/**
	 * all the FilterGroup will be joint by AND, this is meant for how to join the individual filter inside each
	 * FilterGroup
	 * 
	 * @return AND/OR, by default it will return "OR"
	 */
	public String getLogicalOperator();

	/**
	 * flexiable method to provide metadata for different displaystyle and type with single interface
	 * 
	 * @param key
	 * @return value for the key
	 */
	public String getMetaData(String key);

	public List<ProductAdvisorFilter> getFilters();

}

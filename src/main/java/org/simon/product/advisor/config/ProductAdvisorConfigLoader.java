package org.simon.product.advisor.config;

import java.util.List;

import org.simon.product.advisor.config.impl.ProductAdvisorConfigXmlImpl;

/**
 * POJO object for advisor configuration xml
 * 
 * @author Simon.Yang
 * 
 */
public interface ProductAdvisorConfigLoader {

	public ProductAdvisorConfigXmlImpl load(String configFilePath) throws Exception;

	public List<ProductAdvisorConfigXmlImpl> loadAll() throws Exception;

	public ProductAdvisorConfigXmlImpl loadDefault() throws Exception;

	public void reload() throws Exception;
}

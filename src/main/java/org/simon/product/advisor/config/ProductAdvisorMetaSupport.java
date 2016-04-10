package org.simon.product.advisor.config;

import java.util.Map;

public class ProductAdvisorMetaSupport {

	private Map metaDatas;

	public Map getMetaDatas() {
		return metaDatas;
	}

	public void setMetaDatas(Map metaDatas) {
		this.metaDatas = metaDatas;
	}

	public String getMetaData(String key) {
		String value = (String) this.getMetaDatas().get(key);
		return (value == null ? key : value);
	}
}

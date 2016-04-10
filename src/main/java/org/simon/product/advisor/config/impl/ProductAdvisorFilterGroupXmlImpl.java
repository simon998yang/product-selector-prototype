package org.simon.product.advisor.config.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simon.product.advisor.config.ProductAdvisorFilter;
import org.simon.product.advisor.config.ProductAdvisorFilterGroup;
import org.simon.product.advisor.config.ProductAdvisorMetaSupport;

public class ProductAdvisorFilterGroupXmlImpl extends ProductAdvisorMetaSupport implements ProductAdvisorFilterGroup {
	private String id;

	private String questionStyle;

	private String logicalOperator;

	private String enableFor;

	private List originFilters;

	private Map filterLocaleMap = new HashMap();

	public void setOriginFilters(List filters) {
		this.originFilters = filters;
	}

	public List<ProductAdvisorFilter> getFilters() {
		return this.originFilters;
	}

	public String getEnableFor() {
		return enableFor;
	}

	public void setEnableFor(String enableFor) {
		this.enableFor = enableFor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

	public String getQuestionStyle() {
		return questionStyle;
	}

	public void setQuestionStyle(String questionStyle) {
		this.questionStyle = questionStyle;
	}

	public List getOriginFilters() {
		return originFilters;
	}

}

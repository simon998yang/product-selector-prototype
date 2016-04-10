package org.simon.product.advisor.config.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.simon.product.advisor.config.ProductAdvisorConfig;
import org.simon.product.advisor.config.ProductAdvisorFilter;
import org.simon.product.advisor.config.ProductAdvisorFilterGroup;
import org.simon.product.advisor.config.ProductAdvisorMetaSupport;

public class ProductAdvisorConfigXmlImpl extends ProductAdvisorMetaSupport implements ProductAdvisorConfig {
	private String id;

	private List categories;

	private String enableFor;

	private List originFilterGroups;

	private Map translations;

	public List getCategories() {
		return categories;
	}

	public void setCategories(List categories) {
		this.categories = categories;
	}

	public Map getTranslations() {
		return translations;
	}

	public void setTranslations(Map translations) {
		this.translations = translations;
	}

	public void setOriginFilterGroups(List filterGroups) {
		this.originFilterGroups = filterGroups;
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

	public List<ProductAdvisorFilterGroup> getFilterGroups() {
		return this.originFilterGroups;
	}

	public String getMessage(String key, Locale locale) {
		Map keys = (Map) this.translations.get(key);
		if (keys != null) {
			return (String) keys.get(locale.toString());
		}
		return key;
	}

	public List<ProductAdvisorFilterGroup> getOriginFilterGroups() {
		return this.originFilterGroups;
	}

	// TODO:should change to map instead of list for filter retrieval
	public ProductAdvisorFilter getFilter(String id) {
		Iterator it = originFilterGroups.iterator();
		while (it.hasNext()) {
			ProductAdvisorFilterGroup filterGroup = (ProductAdvisorFilterGroup) it.next();
			Iterator itInner = filterGroup.getFilters().iterator();
			while (itInner.hasNext()) {
				ProductAdvisorFilter filter = (ProductAdvisorFilter) itInner.next();
				if (filter.getId().equalsIgnoreCase(id))
					return filter;
			}
		}
		return null;
	}
}

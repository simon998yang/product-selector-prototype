package org.simon.product.advisor.config.impl;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.ArrayList;
import java.util.List;

import org.simon.product.Product;
import org.simon.product.ProductCategory;
import org.simon.product.advisor.FilterEngine;
import org.simon.product.advisor.config.ProductAdvisorFilter;
import org.simon.product.advisor.config.ProductAdvisorMetaSupport;
import org.simon.util.FileToolBox;

public class ProductAdvisorFilterXmlImpl extends ProductAdvisorMetaSupport implements ProductAdvisorFilter {

	public static String RULE_TYPE_SKULIST = "skulist";

	public static String RULE_TYPE_RANGE = "range";

	private String id;

	private String enableFor;

	private String ruleType;

	private String ruleValue;

	private FilterEngine engine;

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

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	/**
	 * Use groovy class to execute user defined rules in groovy syntax
	 * 
	 * @param ruleValue
	 */
	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;

		if (!RULE_TYPE_SKULIST.equalsIgnoreCase(this.ruleType)) {
			// TODO: should not load groovy file here, should load it only once
			// for all rules
			GroovyShell shell = new GroovyShell();
			String file = FileToolBox.findInClasspath("org/simon/product/advisor/FilterEngineImpl.groovy");
			String openFile = "new File(file).text";
			Script openFileScript = shell.parse(openFile);
			openFileScript.setProperty("file", file);

			String groovyText = (String) openFileScript.run();
			groovyText = groovyText.replace("TOBEREPLACE", ruleValue);
			GroovyClassLoader gcl = (GroovyClassLoader) openFileScript.getClass().getClassLoader();
			Class newClass = gcl.parseClass(groovyText);

			try {
				engine = (FilterEngine) newClass.newInstance();
			} catch (InstantiationException e) {
				// TODO: handle exception properly
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO: handle exception properly
				e.printStackTrace();
			}
		}

	}

	public List<Product> getCandidateProducts(ProductCategory category, Object min, Object max) {
		ArrayList candidates = new ArrayList();
		List<Product> products = category.getProducts();
		for (Product product : products) {
			if (RULE_TYPE_SKULIST.equalsIgnoreCase(this.ruleType)) {
				if (this.ruleValue.indexOf(product.getId()) >= 0) {
					candidates.add(product);
				}
			} else {
				Object value = false;
				try {
					value = engine.applyRule(product, min, max);
				} catch (Throwable e) {
					// TODO:log exception
					e.printStackTrace();
				}
				if (Boolean.TRUE.equals(value)) {
					candidates.add(product);
				}
			}

		}
		return candidates;
	}

}

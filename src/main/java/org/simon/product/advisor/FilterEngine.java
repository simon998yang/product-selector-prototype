package org.simon.product.advisor;

import org.simon.product.Product;

/**
 * Filter Engine apply the rule defined in advisor configuration file on Product to determine whether this particular
 * product is selected based on the rule
 * 
 * @author Simon
 *
 */
public interface FilterEngine {
	/**
	 * 
	 * @param proudct
	 *            Product to be applied rule upon
	 * @param min
	 *            when the rule type is range, i.e a price range, this would be the min value from front end
	 * @param max
	 *            max value
	 * @return whether this product is selected for this rule
	 */
	boolean applyRule(Product proudct, Object min, Object max);
}

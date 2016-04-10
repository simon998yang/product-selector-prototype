package org.simon.web;

import java.util.List;

import org.simon.product.ProductCategory;
import org.simon.product.ProductService;
import org.simon.product.advisor.ProductFilterService;
import org.simon.product.advisor.config.ProductAdvisorConfig;
import org.simon.product.advisor.config.ProductAdvisorConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class ProductAdvisorController {

	private ProductService productService;
	private ProductAdvisorConfigLoader configLoader;
	private WebUtil webUtil;
	@Autowired
	private ProductFilterService filterService;

	@Autowired
	public ProductAdvisorController(ProductService productService, ProductAdvisorConfigLoader configLoader,
			WebUtil webUtil) {
		this.productService = productService;
		this.configLoader = configLoader;
		this.webUtil = webUtil;
	}

	/**
	 * URL for reload configuration xml
	 * 
	 * @return
	 */
	@RequestMapping("/reload")
	@ResponseBody
	String home() {
		try {
			this.configLoader.reload();
		} catch (Exception e) {
			return e.getMessage();
		}
		return "reloaded successfully!";
	}

	/**
	 * URL to handle "range" filter
	 * 
	 * @param range
	 *            user selected "minvalue,maxvalue"
	 * @param filterId
	 * @return selected products' ids as string "id1,id2,id3"
	 * 
	 */
	@RequestMapping("/rangeFilter")
	@ResponseBody
	String rangeFilter(@RequestParam(value = "range", required = true) String range,
			@RequestParam(value = "filterId", required = true) String filterId) {
		return this.filterService.filter(this.productService.getProductCategory(""), filterId, range);
	}

	@RequestMapping("/advisor")
	String advisor() {
		return "index";
	}

	@ModelAttribute("products")
	public List products() {
		return this.productService.getProductCategory("").getProducts();
	}

	@ModelAttribute("category")
	public ProductCategory category() {
		return this.productService.getProductCategory("");
	}

	@ModelAttribute("advisorConfig")
	public ProductAdvisorConfig getAdvisorConfig() {
		try {
			return this.configLoader.loadDefault();
		} catch (Exception e) {
			// TODO handle exception properly
			e.printStackTrace();
			return null;
		}
	}

	@ModelAttribute("webUtil")
	public WebUtil webUtil() {
		return this.webUtil;
	}
}

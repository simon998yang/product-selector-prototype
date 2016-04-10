package org.simon.product.advisor.config.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.simon.product.advisor.config.ProductAdvisorConfigLoader;
import org.simon.util.FileToolBox;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

@Service
@EnableAutoConfiguration
public class ProductAdvisorConfigLoaderXmlImpl implements ProductAdvisorConfigLoader {

	private String mappingPath;

	private boolean fileIsInClassPath;

	private String advisorConfigPath;

	private ProductAdvisorConfigXmlImpl config = null;

	public List<ProductAdvisorConfigXmlImpl> loadAll() throws Exception {
		List<ProductAdvisorConfigXmlImpl> configs = new ArrayList<ProductAdvisorConfigXmlImpl>();

		File dir = new File(this.getClass().getClassLoader().getResource(advisorConfigPath).toURI());
		if (!dir.exists()) {
			throw new Exception("No configuration available for product advisor.");
		}
		String[] files = dir.list();
		for (int i = 0; i < files.length; i++) {
			if (files[i].endsWith(".xml"))
				configs.add(load(advisorConfigPath + "/" + files[i]));
		}
		return configs;
	}

	public ProductAdvisorConfigXmlImpl load(String configFilePath) throws IOException, MappingException,
			MarshalException, ValidationException {
		Mapping mapping = new Mapping();

		loadMapping(mapping, mappingPath);

		Unmarshaller unmar = new Unmarshaller(mapping);

		InputStream inputStream;
		if (fileIsInClassPath)
			inputStream = FileToolBox.findResourceInClasspath(configFilePath);
		else
			inputStream = new FileInputStream(configFilePath);
		InputSource is = new InputSource(inputStream);
		ProductAdvisorConfigXmlImpl config = (ProductAdvisorConfigXmlImpl) unmar.unmarshal(is);

		return config;
	}

	private void loadMapping(Mapping mapping, String path) throws IOException, MappingException {
		InputStream inputStream = FileToolBox.findResourceInClasspath(path);
		InputSource inputSourceMapping = new InputSource(inputStream);

		mapping.loadMapping(inputSourceMapping);

	}

	public void setFileIsInClassPath(boolean fileIsInClassPath) {
		this.fileIsInClassPath = fileIsInClassPath;
	}

	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}

	public void setAdvisorConfigPath(String advisorConfigPath) {
		this.advisorConfigPath = advisorConfigPath;
	}

	@Override
	public ProductAdvisorConfigXmlImpl loadDefault() throws Exception {
		if (this.config == null) {
			this.setMappingPath("org/simon/product/advisor/HPProductAdvisorMapping.xml");
			this.setFileIsInClassPath(true);
			this.setAdvisorConfigPath("org/simon/product/advisor");
			this.config = this.load("org/simon/product/advisor/product-advisor.xml");
		}
		return config;
	}

	public void reload() throws Exception {
		this.config = null;
		this.loadDefault();
	}

}

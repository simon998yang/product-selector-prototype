package org.simon.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.simon.product.Product;
import org.simon.util.FileToolBox;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

@EnableAutoConfiguration
@Service
public class WebUtil {
	/**
	 * format product ids as string for front end
	 * 
	 * @param products
	 * @return product IDs as "id1,id2,id3,"
	 */
	public String getIDsAsString(List<Product> products) {
		String s = "";
		Iterator<Product> it = products.iterator();
		while (it.hasNext()) {
			s += ((Product) it.next()).getId() + ",";
		}
		return s;
	}

	public String loadConfigFileAsString() {
		Scanner scanner = null;
		try {
			String file = FileToolBox.findInClasspath("org/simon/product/advisor/product-advisor.xml");
			scanner = new Scanner(new File(file));
			String text = scanner.useDelimiter("\\A").next();
			return text;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				scanner.close();
			} catch (Exception ex) {
			}
		}
		return "";
	}

	public String saveConfig(String config) {
		String file = FileToolBox.findInClasspath("org/simon/product/advisor/product-advisor.xml");
		PrintWriter out = null;
		try {
			out = new PrintWriter(file);
			out.print(config);
		} catch (FileNotFoundException e) {
			return "Cannot save file! " + e.getMessage();
		} finally {
			if (out != null)
				out.close();
		}
		return "Saved successfully!";
	}
}

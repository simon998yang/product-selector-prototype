package org.simon.web;

import junit.framework.TestCase;

public class WebUtilTest extends TestCase {
	public void testLoadConfigFileAsString() {
		String s = new WebUtil().loadConfigFileAsString();
	}

}

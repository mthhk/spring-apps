package com.jd.ipc.inner.softSwitch.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SoftSwitchNameSpaceHandler extends NamespaceHandlerSupport {
	private static final Log LOG = LogFactory.getLog(SoftSwitchNameSpaceHandler.class);

	public void init() {
		SoftSwitchXmlTagParser parser = new SoftSwitchXmlTagParser();

		LOG.info("init handler(" + parser.getClass().getName() + ") for tag <annotation-dirven>");

		registerBeanDefinitionParser("annotation-dirven", parser);
	}

}

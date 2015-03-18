package com.jd.ipc.inner.softSwitch.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.jd.ipc.inner.softSwitch.annotation.SoftSwitch;
import com.jd.ipc.inner.softSwitch.funcation.SwitchSupportService;
import com.jd.ipc.inner.softSwitch.support.SwitchAdvice;

class SoftSwitchXmlTagParser implements BeanDefinitionParser {
	private static final String SWITCH_ADVICE_BEAN_NAME = "com.jd.ipc.inner.switch.switchAdvice";
	private static final String SWITCH_ADVISOR_BEAN_NAME = "com.jd.ipc.inner.switch.switchAdvisor";
	private static final String BASE_PACKAGE = "base-package";
	public static final String SWITCH_SUPPORT_SERVICE_NAME = "switchSupportService-name";
	private static final Log log = LogFactory.getLog(SoftSwitchXmlTagParser.class);

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		log.info("parsing element...");
		// 注册creator（如果有必要）
		AopNamespaceUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(parserContext, element);

		// 注册增强
		BeanDefinitionRegistry registry = parserContext.getRegistry();
		RootBeanDefinition root = new RootBeanDefinition();
		root.setBeanClass(SwitchAdvice.class);

		BeanDefinition switchSupportServiceDef = getSoftSwitchServiceDef(element, parserContext);

		// 引用判断某Key是否开启的service-bean
		root.getPropertyValues().addPropertyValue("switchSupportService", switchSupportServiceDef);

		BeanDefinitionHolder profilerDefinitionHolder = new BeanDefinitionHolder(root, SWITCH_ADVICE_BEAN_NAME);

		BeanDefinitionReaderUtils.registerBeanDefinition(profilerDefinitionHolder, registry);

		registAdvisor(element, parserContext);

		return null;
	}

	/**
	 * 获取{@link SwitchSupportService}的实例
	 */
	private BeanDefinition getSoftSwitchServiceDef(Element element, ParserContext parserContext) {
		String name = element.getAttribute(SWITCH_SUPPORT_SERVICE_NAME);
		BeanDefinition def = parserContext.getRegistry().getBeanDefinition(name);
		if (def == null) {
			throw new RuntimeException(SwitchSupportService.class.getName() + "的实例必须在此之前被配置");
		}
		return def;
	}

	/**
	 * 注册软开关功能必需的一些advisor-bean
	 * 
	 */
	private void registAdvisor(Element element, ParserContext parserContext) {
		RootBeanDefinition advisorDef = new RootBeanDefinition(DefaultBeanFactoryPointcutAdvisor.class);
		advisorDef.setSource(parserContext.extractSource(element));
		advisorDef.getPropertyValues().add("adviceBeanName", new RuntimeBeanNameReference(SWITCH_ADVICE_BEAN_NAME));

		String basePackage = element.getAttribute(BASE_PACKAGE);

		log.info("soft-switch : base package : " + basePackage);

		String expression = getExpression(basePackage);

		log.info("expression : " + expression);

		// 切面表达式注册
		RootBeanDefinition expressionBeanDefinition = new RootBeanDefinition(AspectJExpressionPointcut.class);
		expressionBeanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		expressionBeanDefinition.setSynthetic(true);

		expressionBeanDefinition.getPropertyValues().add("expression", expression);
		expressionBeanDefinition.setSource(parserContext.extractSource(element));

		advisorDef.getPropertyValues().add("pointcut", expressionBeanDefinition);

		// 完成注册
		parserContext.getRegistry().registerBeanDefinition(SWITCH_ADVISOR_BEAN_NAME, advisorDef);

	}

	private String getExpression(String basePackage) {
		String expression = "execution(@" + SoftSwitch.class.getName() + " * " + basePackage + "..*(..))";

		log.debug(expression);

		return expression;
	}
}

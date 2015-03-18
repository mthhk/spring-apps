package com.jd.ipc.inner.softSwitch.support;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.jd.ipc.inner.softSwitch.annotation.SoftSwitch;
import com.jd.ipc.inner.softSwitch.funcation.FuncationPausedException;
import com.jd.ipc.inner.softSwitch.funcation.SwitchSupportService;

/**
 * 软开关功能实现。</br>通过{@linkplain SwitchSupportService}的实例判断当前key下的功能是否开启。如果没有开启，则触发
 * {@link FuncationPausedException}
 * 
 * @author jiali1
 * 
 */
public class SwitchAdvice implements MethodInterceptor {
	private static final Log LOGGE = LogFactory.getLog(SwitchAdvice.class);
	SwitchSupportService switchSupportService;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		boolean isOpen = true;

		// 获取key
		String switchKey = getSwitchKey(invocation);

		// 根据服务，查看该key是否被暂停
		if (StringUtils.hasText(switchKey)) {
			isOpen = switchSupportService.isOpen(switchKey);
		}
		if (isOpen) {
			LOGGE.info("funcation is open");

			// 允许执行
			return invocation.proceed();
		} else {
			LOGGE.info("funcation is paused");
			throw new FuncationPausedException("该功能现在处于暂停状态!");
		}
	}

	/**
	 * 获取目标方法的soft-switch key。</br> 方法级别的配置将会覆盖class级别的配置。
	 * 
	 * @param invocation
	 * @return
	 */
	private String getSwitchKey(MethodInvocation invocation) {
		String key = null;
		SoftSwitch methodAnnotation = invocation.getMethod().getAnnotation(SoftSwitch.class);
		key = methodAnnotation.value();
		if (!StringUtils.hasText(key)) {
			SoftSwitch classAnnotation = invocation.getThis().getClass().getAnnotation(SoftSwitch.class);
			String classKey = classAnnotation.value();
			if (StringUtils.hasText(classKey)) {
				key = classKey;
			}
		}
		return key;
	}

	public void setSwitchSupportService(SwitchSupportService switchSupportService) {
		this.switchSupportService = switchSupportService;
	}
}

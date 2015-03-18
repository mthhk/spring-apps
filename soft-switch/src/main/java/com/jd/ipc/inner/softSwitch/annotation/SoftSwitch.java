package com.jd.ipc.inner.softSwitch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 软开关注解。</br> 可以运用于方法及类上面。</br> example： <code><pre>
 * <b>@SoftSwitch("my_switch_key")</b>
 * public class MyService{
 * 	<b>@SoftSwitch("ept-worker-enabled")</b>
 * 	public void myMethod(){
 * 	}
 * }
 * </pre></code>其中方法级别的注解会重载class级别的注解
 * 
 * @author jiali1
 * 
 */
@Inherited
@Documented
@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SoftSwitch {
	/**
	 * 控制软开关的key。
	 * 
	 * @return
	 */
	public String value() default "";
}

package com.jd.ipc.inner.softSwitch.funcation;

/**
 * 软开关的接口。</br> 这个接口拥有最高级别的决定权。这意味着其他方式（注解）不会覆盖{@link #isOpen()}所给的反馈。
 * 
 * @see #isOpen()
 * @author jiali1
 * 
 */
public interface Switchable {
	/**
	 * 是否开启这个类中所有功能的总开关</b>
	 * 
	 * @return true:开启；false:暂时关闭
	 */
	public boolean isOpen();
}

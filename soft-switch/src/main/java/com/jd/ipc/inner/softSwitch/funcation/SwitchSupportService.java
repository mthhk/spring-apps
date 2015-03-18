package com.jd.ipc.inner.softSwitch.funcation;

/**
 * 软开关的配置Service</br>
 * 
 * @see #isOpen(String)
 * @author jiali1
 * 
 */
public interface SwitchSupportService {
	/**
	 * 返回指定key所对应的功能是否开放
	 * 
	 * @param switchKey
	 * @return 该Key对应的功能是否开启
	 */
	public boolean isOpen(String switchKey);
}

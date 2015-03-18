package com.jd.ipc.inner.service.impl;

import org.springframework.stereotype.Service;

import com.jd.ipc.inner.softSwitch.annotation.SoftSwitch;

/**
 * Hello world!
 * 
 */
@Service
@SoftSwitch("on")
public class MyServiceExt {
	@SoftSwitch("on-1")
	public int on1() {
		System.out.println("operation a");
		return 1;
	}

	@SoftSwitch("off-1")
	public int off1() {
		System.out.println("operation b");
		return 0;
	}

	public void operationE() {
		System.err.println("不应该处于监控中");
	}

}

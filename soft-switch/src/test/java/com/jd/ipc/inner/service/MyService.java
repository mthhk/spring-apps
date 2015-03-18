package com.jd.ipc.inner.service;

import org.springframework.stereotype.Service;

import com.jd.ipc.inner.softSwitch.annotation.SoftSwitch;

/**
 * Hello world!
 * 
 */
@Service
public class MyService {
	@SoftSwitch("on-0")
	public int on0() {
		System.out.println("operation a");
		return 1;
	}

	@SoftSwitch("on-1")
	public int on1() {
		System.out.println("operation b");
		return 1;
	}

	@SoftSwitch("off-1")
	public int off1() {
		System.out.println("operation b");
		return 0;
	}

	public int operationE() {
		System.err.println("不应该被暂停");
		return 1;
	}
}

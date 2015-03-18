package com.jd.ipc.inner.service1;

import org.springframework.stereotype.Service;

import com.jd.ipc.inner.softSwitch.annotation.SoftSwitch;

@Service
public class EvilService {
	@SoftSwitch("off1")
	public int operation() {
		System.out.println("我不该被暂停");
		return 1;
	}
}

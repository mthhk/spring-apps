package com.jd.ipc.inner;

import org.springframework.stereotype.Service;

import com.jd.ipc.inner.softSwitch.funcation.SwitchSupportService;

@Service("switchSupportService")
public class SoftSwitchSupportServiceImpl implements SwitchSupportService {

	public boolean isOpen(String switchKey) {
		return switchKey.contains("on");
	}

}

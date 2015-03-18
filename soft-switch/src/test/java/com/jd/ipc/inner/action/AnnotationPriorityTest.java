package com.jd.ipc.inner.action;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jd.ipc.inner.service.MyService;
import com.jd.ipc.inner.service.impl.MyServiceExt;
import com.jd.ipc.inner.service1.EvilService;
import com.jd.ipc.inner.softSwitch.funcation.FuncationPausedException;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class AnnotationPriorityTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	MyServiceExt myServiceExt;
	@Autowired
	MyService myService;
	@Autowired
	EvilService evilService;

	@Test
	public void testPriority() {
		Assert.assertEquals(myService.on0(), 1);
		Assert.assertEquals(myService.on1(), 1);

		Assert.assertEquals(myServiceExt.on1(), 1);
	}

	@Test(expected = FuncationPausedException.class)
	public void testPaused() {
		myService.off1();
	}

	@Test(expected = FuncationPausedException.class)
	public void testPaused1() {
		myServiceExt.off1();
	}

	@Test
	public void testScope() {
		Assert.assertEquals(evilService.operation(), 1);
	}
}

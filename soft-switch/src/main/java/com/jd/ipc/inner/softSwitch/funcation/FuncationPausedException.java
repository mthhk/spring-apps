package com.jd.ipc.inner.softSwitch.funcation;

/**
 * 此exception表示某功能被停用。</br> 在调用某个被停用的Service（或方法）时被触发。调用者可以通过捕获该异常以做出响应
 * 
 * @author jiali1
 * 
 */
public class FuncationPausedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FuncationPausedException() {
		super();
	}

	public FuncationPausedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FuncationPausedException(String message) {
		super(message);
	}

	public FuncationPausedException(Throwable cause) {
		super(cause);
	}

}

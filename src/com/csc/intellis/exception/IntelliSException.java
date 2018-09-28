package com.csc.intellis.exception;

public class IntelliSException extends Exception {

	private static final long serialVersionUID = -5615302271493112241L;

	private String errNum = null;

	public IntelliSException(String errNum, String message, Throwable throwable) {
		super(message, throwable);
		this.errNum = errNum;
	}

	public IntelliSException(String errNum, String message) {
		super(message);
		this.errNum = errNum;
	}

	public IntelliSException(String errNum, Throwable throwable) {
		super(throwable);
		this.errNum = errNum;
	}

	public IntelliSException(String message) {
		super(message);
	}

	public String getErrorNum() {
		return errNum;
	}
}
package org.rsr;

/**
 * Exception class for all throws exceptions within RSR
 * 
 * @author Joe Hudson
 */
public class RsrException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RsrException() {
		super();
	}

	public RsrException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RsrException(String arg0) {
		super(arg0);
	}

	public RsrException(Throwable arg0) {
		super(arg0);
	}

}

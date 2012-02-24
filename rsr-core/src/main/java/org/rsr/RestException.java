package org.rsr;

public class RestException extends RuntimeException {

	public RestException() {
		super();
	}

	public RestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RestException(String arg0) {
		super(arg0);
	}

	public RestException(Throwable arg0) {
		super(arg0);
	}

}

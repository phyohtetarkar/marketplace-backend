package com.marketplace.domain;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ErrorCodes code;
	
	public static ApplicationException notFound(String message) {
		return new ApplicationException(ErrorCodes.NOT_FOUND, message);
	}

	public ApplicationException() {
	}

	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(ErrorCodes code, String message) {
		super(message);
		this.code = code;
	}

}

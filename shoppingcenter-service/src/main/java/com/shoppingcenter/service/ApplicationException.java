package com.shoppingcenter.service;

import java.util.List;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	private List<String> messages;

	public ApplicationException() {
		super();
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

	public ApplicationException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ApplicationException(String code, List<String> messages) {
		this.code = code;
		this.messages = messages;
	}

}

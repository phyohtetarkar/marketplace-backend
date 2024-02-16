package com.marketplace.domain;

public class FileIOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public FileIOException() {
    }

    public FileIOException(String arg0) {
        super(arg0);
    }

    public FileIOException(Throwable arg0) {
        super(arg0);
    }

    public FileIOException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public FileIOException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}

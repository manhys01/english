package dev.manhnd.english.dao;

public class DuplicateUniqueColumnException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateUniqueColumnException() {
		super();
	}

	public DuplicateUniqueColumnException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateUniqueColumnException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateUniqueColumnException(String message) {
		super(message);
	}

	public DuplicateUniqueColumnException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}

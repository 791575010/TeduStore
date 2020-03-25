package cn.tedu.store.service.ex;

public class UsernameConfictException extends ServiceException {

	public UsernameConfictException() {
		super();
	}

	public UsernameConfictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsernameConfictException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameConfictException(String message) {
		super(message);
	}

	public UsernameConfictException(Throwable cause) {
		super(cause);
	}
	
}

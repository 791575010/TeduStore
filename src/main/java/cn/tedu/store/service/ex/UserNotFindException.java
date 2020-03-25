package cn.tedu.store.service.ex;

public class UserNotFindException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5411670956201833787L;

	public UserNotFindException() {
		super();
	}

	public UserNotFindException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserNotFindException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFindException(String message) {
		super(message);
	}

	public UserNotFindException(Throwable cause) {
		super(cause);
	}
	
	

}

package cn.tedu.store.service.ex;

public class PasswordNotMacthException extends ServiceException {

	private static final long serialVersionUID = -4482071569587798469L;

	public PasswordNotMacthException() {
		super();
	}

	public PasswordNotMacthException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordNotMacthException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordNotMacthException(String message) {
		super(message);
	}

	public PasswordNotMacthException(Throwable cause) {
		super(cause);
	}

	
}

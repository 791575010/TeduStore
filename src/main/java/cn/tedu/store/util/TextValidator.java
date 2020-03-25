package cn.tedu.store.util;

public final class TextValidator {
	
	private TextValidator() {
		
	}
	
	private static final String REGEX_USERNAME = "[a-zA-Z]{1}[a-zA-Z0-9_]{3,15}";
	private static final String REGEX_PHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
	private static final String REGEX_EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	
	public static boolean checkUsername(String username) {
		if(username==null) {
			return false;
		}
		return username.matches(REGEX_USERNAME);
	}
	
	public static boolean checkPassword(String password) {
		if(password==null) {
			return false;
		}
		return password.length()>4 && password.length()<16;
	}
	
	public static boolean checkEmail(String email) {
		if(email==null) {
			return false;
		}
		return email.matches(REGEX_EMAIL);
	}
	
	public static boolean checkPhone(String phone) {
		if(phone==null) {
			return false;
		}
		return phone.matches(REGEX_PHONE);
	}
}

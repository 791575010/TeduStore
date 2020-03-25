package cn.tedu.store.entiy;

public class User extends BaseEntiy{


	private Integer id;
	
	private String username;
	
	private String password;
	
	private String avatar;
	
	private String salt;
	
	private Integer gender;
	
	private String phone;
	
	private String email;
	
	private String isDelete;
	

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "UserEntiy [id=" + id + ", username=" + username + ", password=" + password + ", avatar=" + avatar
				+ ", gender=" + gender + ", phone=" + phone + ", email=" + email + ", isDelete=" + isDelete + "]";
	}
	
	
	
	
	

}

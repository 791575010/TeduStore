package cn.tedu.store.entiy;

public class ResponseResult<T> {
	
	private Integer state = 200;
	
	private String message;
	
	private T data;
	

	public ResponseResult() {
	}
	
	

	public ResponseResult(Integer state, Exception e) {
		this.state = state;
		this.message = e.getMessage();
	}
		
	


	public ResponseResult(Integer state, String message) {
		super();
		this.state = state;
		this.message = message;
	}



	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	

}

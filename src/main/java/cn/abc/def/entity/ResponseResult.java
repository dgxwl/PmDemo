package cn.abc.def.entity;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer state;
	private String message;
	private T data;
	
	public ResponseResult() {
		
	}

	public ResponseResult(Integer state, String message, T data) {
		this.state = state;
		this.message = message;
		this.data = data;
	}

	public ResponseResult(Integer state, String message) {
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

package com.userservice.response;

public class ApiResponse {

	private Object data;
	private String message;
	private String filePath;
	private String status;

	public ApiResponse() {
	}

	public ApiResponse(Object data, String message, String filePath, String status) {
		this.data = data;
		this.message = message;
		this.filePath = filePath;
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ApiResponse [data=" + data + ", message=" + message + ", filePath=" + filePath + ", status=" + status
				+ "]";
	}

}

package org.hebgb.app.cms.commons;

public class AjaxResult {
	public static final int SUCCESS_CODE = 0;
	public static final int DEFAULT_ERROR_CODE = 100;

	public AjaxResult() {

	}

	public AjaxResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private int code = SUCCESS_CODE;
	private String message;
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

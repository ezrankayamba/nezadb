package tz.co.nezatech.apps.util.nezadb.model;

public class Status {
	private int code;
	private String message;
	private int generatedId;
	private boolean success = false;

	public Status(int code, String message) {
		this(code, message, 0);
	}

	public Status(int code, String message, int generatedId) {
		super();
		this.code = code;
		this.message = message;
		this.generatedId = generatedId;
		this.success = code == 200;
	}

	public int getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(int generatedId) {
		this.generatedId = generatedId;
	}

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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}

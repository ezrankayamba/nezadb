package tz.co.nezatech.apps.util.nezadb.model;

public class Status {
	private int code;
	private String message;
	private long generatedId;

	public Status(int code, String message) {
		this(code, message, 0);
	}

	public Status(int code, String message, int generatedId) {
		super();
		this.code = code;
		this.message = message;
		this.generatedId = generatedId;
	}

	public long getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(long generatedId) {
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
		return getCode()==200;
	}
}

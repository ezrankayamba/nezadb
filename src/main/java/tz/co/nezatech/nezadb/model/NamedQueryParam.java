package tz.co.nezatech.nezadb.model;

public class NamedQueryParam {
	public enum Operator {
		EQ, NEQ, LIKE, ISNULL, ISNOTNULL, NA
	}

	String name;
	Object value;
	Operator op;
	protected NamedQueryParam() {}
	protected NamedQueryParam(String name, Object value, Operator op) {
		super();
		this.name = name;
		this.value = value;
		this.op = op;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Operator getOp() {
		return op;
	}
	public void setOp(Operator op) {
		this.op = op;
	}
	
}

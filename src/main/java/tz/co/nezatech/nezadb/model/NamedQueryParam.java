package tz.co.nezatech.nezadb.model;

public class NamedQueryParam {
	public enum Operator {
		EQ("="), NEQ("!="), LIKE("LIKE"), ISNULL("IS NULL"), ISNOTNULL("IS NOT NULL"), NA("");

		private final String name;

		private Operator(String s) {
			name = s;
		}

		public boolean equalsName(String otherName) {
			return name.equals(otherName);
		}

		public String toString() {
			return this.name;
		}
	}

	String name;
	Object value;
	Operator op;

	public NamedQueryParam() {
	}

	public NamedQueryParam(String name, Object value, Operator op) {
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

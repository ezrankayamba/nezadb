package tz.co.nezatech.apps.util.nezadb.model;

public class NameDescData implements IData {
	String name;
	String desciption;
	
	public NameDescData() {
		super();
	}
	public NameDescData(String name, String desciption) {
		super();
		this.name = name;
		this.desciption = desciption;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
}

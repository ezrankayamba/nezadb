package tz.co.nezatech.nezadb.model;

public class NameDescData implements IData {
	Long id;
	String name;
	String description;
	
	public NameDescData() {
		super();
	}
	public NameDescData(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

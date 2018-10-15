package fr.glowstoner.kim;

public class Variable {
	
	private String name;
	private Object data;
	
	public Variable(String name, Object data) {
		this.setName(name);
		this.setData(data);
	}
	
	public Variable() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

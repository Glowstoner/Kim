package fr.glowstoner.kim.executor;

public class PackageInfo {
	
	private String domain, author, name;
	
	public PackageInfo(String domain, String author, String name) {
		this.setDomain(domain);
		this.setAuthor(author);
		this.setName(name);
	}
	
	public PackageInfo() {
		
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

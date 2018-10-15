package fr.glowstoner.kim.util.search;

import java.util.ArrayList;
import java.util.List;

public class DeepSearch<T extends Recursive<T>> {
	
	private List<T> full = new ArrayList<>();
	
	public DeepSearch(T full) {
		this.search(full);
	}

	public List<T> get() {
		return this.full;
	}
	
	private void search(T item) {
		this.full.add(item);
		
		for(T sub : item.childs()) {
			this.search(sub);
		}
	}
}

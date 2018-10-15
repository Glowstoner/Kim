package fr.glowstoner.kim.util.search;

import java.util.List;

public interface Recursive<T> {
	
	List<T> childs();
	T parent();
	
}

package fr.glowstoner.kim;

import java.util.List;

import fr.glowstoner.kim.executor.Executor;

public abstract class Container implements Executor {
	
	private List<Executor> childs;
	
	public Container() {
		
	}

	
}

package fr.glowstoner.kim.parser;

import fr.glowstoner.kim.executor.Executor;
import fr.glowstoner.kim.lexer.InstructionToken;

public interface Parser<T extends Executor> {

	boolean canParse(InstructionToken token);
	T parse(InstructionToken token);
	
}

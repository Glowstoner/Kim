package fr.glowstoner.kim.parser;

import fr.glowstoner.kim.lexer.InstructionToken;

public interface Parser<T> {

	boolean canParse(InstructionToken token);
	T parse(InstructionToken token);
	
}

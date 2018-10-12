package fr.glowstoner.kim.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.glowstoner.kim.executor.PrintExecutor;
import fr.glowstoner.kim.lexer.InstructionToken;

public class PrintParser implements Parser<PrintExecutor>{

	@Override
	public boolean canParse(InstructionToken token) {
		Pattern p = Pattern.compile("afficher \\((.*)\\);", Pattern.DOTALL);
		Matcher m = p.matcher(token.getBase());
		
		return m.find();
	}

	@Override
	public PrintExecutor parse(InstructionToken token) {
		return null;
	}

}

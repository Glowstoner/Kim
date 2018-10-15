package fr.glowstoner.kim.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.glowstoner.kim.Words;
import fr.glowstoner.kim.executor.PrintExecutor;
import fr.glowstoner.kim.lexer.InstructionToken;

public class PrintParser implements Parser<PrintExecutor>{

	@Override
	public boolean canParse(InstructionToken token) {
		Pattern p = Pattern.compile(Words.PRINT + " \\((.*)\\);", Pattern.DOTALL);
		Matcher m = p.matcher(token.getBase());
		
		return m.find();
	}

	@Override
	public PrintExecutor parse(InstructionToken token) {
		Pattern p = Pattern.compile(Words.PRINT + " \\((.*)\\);", Pattern.DOTALL);
		Matcher m = p.matcher(token.getBase());
		
		if(m.find()) {
			String exp = m.group(1);
			
			PrintType pt = (token.containsToken(InstructionToken.COMMA)) ? PrintType.APPEND :
				PrintType.CONCAT;
			
			
		}
		
		return null;
	}

	private PrintExecutor parseExpression(String text, PrintType type) {
		
	}
	
	private enum PrintType {
		
		APPEND, CONCAT;
		
	}
}

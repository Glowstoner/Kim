package fr.glowstoner.kim.lexer;

import java.util.Map;

public class InstructionToken {

	public static final Token SEMICOLON = new Token(";", TokenType.SEPARATORS);
	
	private Map<Integer, Token> tokens;
	private String base;
	
	public InstructionToken(String base, Map<Integer, Token> tokens) {
		this.setTokens(tokens);
		this.setBase(base);
	}

	public Map<Integer, Token> getTokens() {
		return this.tokens;
	}
	
	public Token getFirstToken() {
		return this.tokens.get(0);
	}
	
	public Token getLastToken() {
		if(this.tokens.isEmpty()) {
			return null;
		}
		
		return this.tokens.get(this.tokens.size() - 1);
	}
	
	public boolean isValidLineInstruction() {
		return this.getLastToken().equals(SEMICOLON);
	}

	public void setTokens(Map<Integer, Token> tokens) {
		this.tokens = tokens;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}
}

package fr.glowstoner.kim.lexer;

import java.util.Map;

public class InstructionToken {
	
	public static final TokenLine COMMA = new TokenLine(",", TokenLineType.SEPARATORS, Token.COMPARABLE);
	
	private Map<Integer, TokenLine> tokens;
	private String base;
	
	public InstructionToken(String base, Map<Integer, TokenLine> tokens) {
		this.setTokens(tokens);
		this.setBase(base);
	}

	public Map<Integer, TokenLine> getTokens() {
		return this.tokens;
	}
	
	public TokenLine getFirstToken() {
		return this.tokens.get(0);
	}
	
	public TokenLine getLastToken() {
		if(this.tokens.isEmpty()) {
			return null;
		}
		
		return this.tokens.get(this.tokens.size() - 1);
	}
	
	public boolean containsToken(TokenLine token) {
		if(token.getStart() != Token.COMPARABLE) {
			throw new Error("Le token spécifié n'est pas comparable !");
		}
		
		for(TokenLine tl : this.tokens.values()) {
			if(tl.equals(token)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void setTokens(Map<Integer, TokenLine> tokens) {
		this.tokens = tokens;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}
}

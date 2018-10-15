package fr.glowstoner.kim.lexer;

public abstract class Token {
	
	public static final int COMPARABLE = -0x02;
	
	private TokenType tokenType;
	
	public Token(TokenType tokenType) {
		this.setTokenType(tokenType);
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public abstract int getStart();
	public abstract int getEnd();
	
	public enum TokenType {
		
		BLOCK, LINE;
		
	}
}

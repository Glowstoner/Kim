package fr.glowstoner.kim.lexer;

public class Token {
	
	private String token;
	private TokenType type;
	
	public Token(String token, TokenType type) {
		this.setToken(token);
		this.setType(type);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "token: "+this.token+", type: "+this.type.name();
	}
}

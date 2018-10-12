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
	public boolean equals(Object obj) {
		if(obj instanceof Token) {
			Token to = (Token) obj;
			
			if(this.token.equals(to.getToken()) && 
					this.type.equals(to.getType())) {
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "token: "+this.token+", type: "+this.type.name();
	}
}

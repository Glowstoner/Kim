package fr.glowstoner.kim.lexer;

public class TokenLine extends Token{
	
	private TokenLineType type;
	private String token;
	private int line;
	
	public TokenLine(String token, TokenLineType type, int line) {
		super(TokenType.LINE);
		
		this.setToken(token);
		this.setType(type);
		
		this.line = line;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenLineType getType() {
		return type;
	}

	public void setType(TokenLineType type) {
		this.type = type;
	}
	
	@Override
	public int getStart() {
		return this.line;
	}

	@Override
	public int getEnd() {
		return this.line;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TokenLine) {
			TokenLine to = (TokenLine) obj;
			
			if(this.token.equals(to.getToken()) && 
					this.type.equals(to.getType())) {
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "token: "+this.token+", type: "+this.type.name() + ", line: " + this.getStart();
	}
}

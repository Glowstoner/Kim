package fr.glowstoner.kim;

public final class Words {

	public static final String VAR = "variable", FUNC = "fonction", DEC = "déclaration", PRINT = "afficher",
			END = "fin", IF = "si", IS = "est", ELSE = "sinon", ELSEIF = "sinon si", EXEC = "éxécution";
	
	public static final String[] KEYWORDS = new String[] {VAR, FUNC, DEC, PRINT, END, IF, IS, ELSE, ELSEIF , EXEC};
	
	public static final String[] OPERATORS = new String[] {"=", "/", "\\*", "-", "\\+", "%", "\\^"};
	public static final String[] SEPARATORS = new String[] {"\\{", "\\}", "\\(", "\\)", ","};
}

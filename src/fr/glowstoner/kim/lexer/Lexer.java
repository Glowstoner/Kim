package fr.glowstoner.kim.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
	
	public static final String[] KEYWORDS = new String[] {"variable", "fonction", "déclaration",
			"afficher", "fin", "si", "est", "sinon", "sinonsi" , "éxecution"};
	
	public static final String[] OPERATORS = new String[] {"=", "/", "\\*", "-", "\\+", "%", "\\^"};
	public static final String[] SEPARATORS = new String[] {"\\{", "\\}", "\\(", "\\)", ";"};
	
	private Map<Integer, List<Token>> tokens = new HashMap<>();
	private Map<Integer, String> blockLines;
	private Map<Integer, String[]> lines;
	private List<String> code;
	private TokenBlock fullBlock;
	
	public Lexer(List<String> code) {
		this.code = code;
		
		this.lines = this.format();
	}
	
	public void parseBlocks() {
		System.out.println("------ BUILD LEXER BLOCK ------");
		
		this.blockLines = this.getMultilineFormatedCode(this.code);
		this.fullBlock = new TokenBlock(null, this.code, 0, this.blockLines.size() - 1);
		
		BlockLexer sbl = new BlockLexer(this.fullBlock);
		sbl.find();
		
		for(TokenBlock tbs : sbl.getFinalToken().getSubBlocks()) {
			System.out.println("FIND child");
			this.buildSubBlockUnit(tbs);
		}
	}
	
	public void registerTokens() {
		for(int i = 0 ; i < this.lines.size() ; i++) {
			List<Token> list = new ArrayList<>();
			
			for(String seq : this.lines.get(i)) {
				System.out.println("check seq -> "+seq);
				
				if(seq.length() == 0) {
					System.out.println("Ligne vide !");
					continue;
				}
				
				boolean end = false;
				
				if(seq.startsWith("\"") && seq.endsWith("\"")) {
					list.add(new Token(seq, TokenType.STRING_LITTERAL));
					System.out.println("put -> string_litteral");
					continue;
				}
				
				try {
					Integer.valueOf(seq);
				
					list.add(new Token(seq, TokenType.INTEGER_LITTERAL));
					
					System.out.println("put -> number (int)");
					
					continue;
				}catch(NumberFormatException ex) {}
				
				for(String keywords : KEYWORDS) {
					if(seq.equals(keywords)) {
						list.add(new Token(seq, TokenType.KEYWORDS));
						System.out.println("put -> keywords -> "+keywords);
						end = true;
						break;
					}
				}
				
				if(end) continue;
				
				if(seq.length() == 1) {
					for(String sep : SEPARATORS) {
						if(sep.replaceAll("\\\\", "").charAt(0) == seq.charAt(0)) {
							list.add(new Token(seq, TokenType.SEPARATORS));
							System.out.println("put -> separators -> "+sep);
							end = true;
							break;
						}
					}
					
					if(end) continue;
					
					for(String op : OPERATORS) {
						if(op.replaceAll("\\\\", "").charAt(0) == seq.charAt(0)) {
							list.add(new Token(seq, TokenType.OPERATORS));
							System.out.println("put -> operators -> "+op);
							end = true;
							break;
						}
					}
					
					if(end) continue;
				}
				
				list.add(new Token(seq, TokenType.IDENTIFIERS));
				System.out.println("put -> identifiers {else}");
			}
			
			this.putTokens(list);
		}
	}
	
	private void putTokens(List<Token> token) {
		this.tokens.put(this.tokens.size(), token);
	}
	
	private Map<Integer, String[]> format() {
		Map<Integer, String[]> map = new HashMap<>();
		
		for(int i = 0 ; i < this.code.size() ; i++) {
			String line = this.code.get(i);
			
			for(String ch : Lexer.OPERATORS) {
				line  = line.replaceAll(ch, " "+ch+" ");
				System.out.println("change operator -> "+ch+", a -> "+line);
			}
			
			for(String ch : Lexer.SEPARATORS) {
				line = line.replaceAll(ch, " "+ch+" ");
				System.out.println("change separator -> "+ch+", a -> "+line);
			}
			
			map.put(map.size(), line.split("\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]"
					+ "|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)"));
		}
		
		return map;
	}
	
	private Map<Integer, String> getMultilineFormatedCode(List<String> code) {
		Map<Integer, String> map = new HashMap<>();
		
		for(String line : code) {
			String form = line.replaceAll("([\"'])(?:(?=(\\\\?))\\2.)*?\\1", "\"\"")
					.replaceAll("\\s+", " ");
			
			map.put(map.size(), form);
		}
		
		return map;
	}
	
	public void showTokens() {
		System.out.println("------------------- RESULTS -------------------");
		
		if(this.tokens.isEmpty()) {
			System.out.println("Aucun token !");
			
			return;
		}
		
		System.out.println("RESULTS TOKENS :");
		
		for(int i = 0 ; i < this.tokens.size() ; i++) {
			if(this.tokens.get(i).isEmpty()) {
				System.out.println("Ligne "+(i + 1)+" : VIDE !");
				continue;
			}
			
			System.out.println("Line "+(i + 1)+" :");
			
			for(int j = 0 ; j < this.tokens.get(i).size() ; j++) {
				System.out.println((j + 1)+" -> "+this.tokens.get(i).get(j));
			}
		}
		
		System.out.println("RESULTS TOKEN BLOCKS :");
		
		this.showTokenBlock(this.fullBlock);
		
		System.out.println("--------------------- END ---------------------");
	}
	
	private void showTokenBlock(TokenBlock t) {
		System.out.println(t.getBlock().toString());
		
		for(TokenBlock tb : t.getSubBlocks()) {
			this.showTokenBlock(tb);
		}
	}
	
	private void buildSubBlockUnit(TokenBlock tb) {
		System.out.println("--- subblock create new unit ---");
		BlockLexer sbu = new BlockLexer(tb);
		sbu.find();
		
		for(TokenBlock tbs : sbu.getFinalToken().getSubBlocks()) {
			System.out.println("FIND child");
			this.buildSubBlockUnit(tbs);
		}
	}
	
	public Map<Integer, InstructionToken> getFinalInstructions() {
		Map<Integer, InstructionToken> map = new HashMap<>();
		
		for(int i = 0 ; i < this.tokens.size() ; i++) {
			if(this.tokens.get(i).isEmpty()) {
				continue;
			}
			
			Map<Integer, Token> tmap = new HashMap<>();
			
			for(int j = 0 ; j < this.tokens.get(i).size() ; j++) {
				tmap.put(j, this.tokens.get(i).get(j));
			}
			
			map.put(i, new InstructionToken(this.code.get(i), tmap));
		}
		
		return map;
	}
	
	public Map<Integer, String[]> getLines() {
		return this.lines;
	}
	
	public Map<Integer, List<Token>> getTokens() {
		return this.tokens;
	}
}
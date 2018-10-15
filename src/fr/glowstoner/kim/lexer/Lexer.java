package fr.glowstoner.kim.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.glowstoner.kim.Class;
import fr.glowstoner.kim.Words;
import fr.glowstoner.kim.util.search.DeepSearch;

public class Lexer {
	
	private Map<Integer, List<TokenLine>> tokens = new HashMap<>();
	private Map<Integer, String> code, blockLines;
	private Map<Integer, String[]> lines;
	private TokenBlock fullBlock;
	private String fileName;
	
	public Lexer(String fileName, List<String> code) {
		System.out.println("Lecture du fichier \"" + fileName + "\"");
		
		this.code = this.toMap(code);
		this.code = this.getBracketsFormatedCode();
		
		this.lines = this.format();
		this.fileName = fileName;
	}
	
	public void parseBlocks() {
		System.out.println("------ BUILD LEXER BLOCK ------");
		
		this.blockLines = this.getMultilineFormatedCode(this.code);
		System.out.println("blockline multiline formated -> "+this.blockLines);
		this.fullBlock = new TokenBlock(null, this.code, 0, this.blockLines.size() - 1);
		
		BlockLexer sbl = new BlockLexer(this.fullBlock);
		sbl.find();
		
		for(TokenBlock tbs : sbl.getFinalToken().getSubBlocks()) {
			System.out.println("FIND child");
			this.buildBlockUnit(tbs);
		}
	}
	
	public void registerTokens() {
		for(int i = 0 ; i < this.lines.size() ; i++) {
			List<TokenLine> list = new ArrayList<>();
			
			for(String seq : this.lines.get(i)) {
				System.out.println("check seq -> "+seq);
				
				if(seq.length() == 0) {
					System.out.println("Ligne vide !");
					continue;
				}
				
				boolean end = false;
				
				if(seq.startsWith("\"") && seq.endsWith("\"")) {
					list.add(new TokenLine(seq, TokenLineType.STRING_LITTERAL, i));
					System.out.println("put -> string_litteral");
					continue;
				}
				
				try {
					Integer.valueOf(seq);
				
					list.add(new TokenLine(seq, TokenLineType.INTEGER_LITTERAL, i));
					
					System.out.println("put -> number (int)");
					
					continue;
				}catch(NumberFormatException ex) {}
				
				for(String keywords : Words.KEYWORDS) {
					if(seq.equals(keywords)) {
						list.add(new TokenLine(seq, TokenLineType.KEYWORDS, i));
						System.out.println("put -> keywords -> "+keywords);
						end = true;
						break;
					}
				}
				
				if(seq.startsWith("@")) {
					list.add(new TokenLine(seq, TokenLineType.VAR_INSTANCE, i));
					System.out.println("put -> var_instance");
					continue;
				}
				
				if(end) continue;
				
				if(seq.length() == 1) {
					for(String sep : Words.SEPARATORS) {
						if(sep.replaceAll("\\\\", "").charAt(0) == seq.charAt(0)) {
							list.add(new TokenLine(seq, TokenLineType.SEPARATORS, i));
							System.out.println("put -> separators -> "+sep);
							end = true;
							break;
						}
					}
					
					if(end) continue;
					
					for(String op : Words.OPERATORS) {
						if(op.replaceAll("\\\\", "").charAt(0) == seq.charAt(0)) {
							list.add(new TokenLine(seq, TokenLineType.OPERATORS, i));
							System.out.println("put -> operators -> "+op);
							end = true;
							break;
						}
					}
					
					if(end) continue;
				}
				
				list.add(new TokenLine(seq, TokenLineType.IDENTIFIERS, i));
				System.out.println("put -> identifiers {else}");
			}
			
			this.putTokens(list);
		}
	}
	
	private void putTokens(List<TokenLine> token) {
		this.tokens.put(this.tokens.size(), token);
	}
	
	public Map<Integer, String> toMap(List<String> code) {
		Map<Integer, String> map = new HashMap<>();
		
		int c = 0;
		
		for(String line : code) {
			if(line.startsWith("#")) {
				continue;
			}
			
			if(!line.isEmpty()) {
				map.put(c, line);
				c++;
			}
		}
		
		return map;
	}
	
	private Map<Integer, String[]> format() {
		Map<Integer, String[]> map = new HashMap<>();
		
		for(int i = 0 ; i < this.code.size() ; i++) {
			String line = this.code.get(i);
			
			for(String ch : Words.OPERATORS) {
				line  = line.replaceAll(ch, " "+ch+" ");
				System.out.println("change operator -> "+ch+", a -> "+line);
			}
			
			for(String ch : Words.SEPARATORS) {
				line = line.replaceAll(ch, " "+ch+" ");
				System.out.println("change separator -> "+ch+", a -> "+line);
			}
			
			map.put(map.size(), line.split("\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]"
					+ "|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)"));
		}
		
		return map;
	}
	
	private Map<Integer, String> getMultilineFormatedCode(Map<Integer, String> code) {
		Map<Integer, String> map = new HashMap<>();
		
		for(int i = 0 ; i < code.size() ; i++) {
			String line = code.get(i);
			String form = line.replaceAll("([\"'])(?:(?=(\\\\?))\\2.)*?\\1", "\"\"")
					.replaceAll("\\s+", " ");
			
			map.put(map.size(), form);
		}
		
		return map;
	}
	
	private Map<Integer, String> getBracketsFormatedCode() {
		//pour les méchants pélos
		/*for(int i = 0 ; i < this.code.size() ; i++) {
			String line = this.code.get(i);
			
			if(line.trim().equals("{")) {
				if(!this.code.get(i - 1).trim().endsWith(";")) {
					
				}else {
					throw new Error("les méchants ne savent pas écrire !");
				}
			}
		}*/
		
		List<String> cv = new ArrayList<>(this.code.values());
		
		for(int i = 0 ; i < cv.size() ; i++) {
			String line = cv.get(i);
			
			if(line.trim().equals("{")) {
				cv.set(i - 1, cv.get(i - 1).concat("{"));
				cv.remove(i);
			}
		}
		
		return this.toMap(cv);
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
		
		for(TokenBlock tb : new DeepSearch<>(this.fullBlock).get()) {
			System.out.println(tb);
		}
		
		System.out.println("--------------------- END ---------------------");
	}
	
	public Class generateClass() {
		Class c = new Class(this.fileName);
		
		c.setTokenLine(this.getFinalInstructions());
		
		Map<Integer, TokenBlock> map = new HashMap<>();
		
		for(TokenBlock tb : new DeepSearch<>(this.fullBlock).get()) {
			map.put(tb.getStart(), tb);
		}
		
		c.setTokenBlock(map);
		
		return c;
	}
	
	private void buildBlockUnit(TokenBlock tb) {
		System.out.println("--- subblock create new unit ---");
		System.out.println("of parent have start=" + tb.getStart() + ", end=" + tb.getEnd());
		BlockLexer sbu = new BlockLexer(tb);
		sbu.find();
		
		for(TokenBlock tbs : sbu.getFinalToken().getSubBlocks()) {
			System.out.println("FIND child");
			this.buildBlockUnit(tbs);
		}
	}
	
	public Map<Integer, InstructionToken> getFinalInstructions() {
		Map<Integer, InstructionToken> map = new HashMap<>();
		
		for(int i = 0 ; i < this.tokens.size() ; i++) {
			if(this.tokens.get(i).isEmpty()) {
				continue;
			}
			
			Map<Integer, TokenLine> tmap = new HashMap<>();
			
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
	
	public Map<Integer, List<TokenLine>> getTokens() {
		return this.tokens;
	}
}
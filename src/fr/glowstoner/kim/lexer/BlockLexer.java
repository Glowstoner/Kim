package fr.glowstoner.kim.lexer;

import java.util.HashMap;
import java.util.Map;

import fr.glowstoner.kim.Words;

public class BlockLexer {

	private TokenBlock tokenBlock;
	
	public BlockLexer(TokenBlock tokenBlock) {
		this.tokenBlock = tokenBlock;
		
		System.out.println("verif string tokenblock");
		System.out.println(this.tokenBlock.toString());
		
		System.out.println("TOKENBLOCK START (real) -> " + 
				this.tokenBlock.getStart() + ", end -> " + this.tokenBlock.getEnd());
	}

	public void find() {
		if(!this.hasMoreBlocks()) {
			System.out.println("pas de blocks valides stop !");
			return;
		}

		for(int i = 0 ; i < this.tokenBlock.getBlock().size() ; i++) {
			String lines = this.tokenBlock.getBlock().get(i);
		
			System.out.println("i="+i+", "+lines);
			
			if(!this.isBlockOrSubBlock(lines)) continue;
			
			int next = this.getSubBlock(i, this.isSubBlockBody(lines));
			
			System.out.println("ajout d'un block avec start="+(this.tokenBlock.getStart() + i + 1) +
					" et end=" + (this.tokenBlock.getStart() + next - 1));
			
			this.tokenBlock.addSubBlock(new TokenBlock(this.tokenBlock,
					this.getBlockLines(i + 1, next - 1), this.tokenBlock.getStart() + i + 1,
					this.tokenBlock.getStart() + next - 1));
			
			if(next == -1) {
				throw new Error("VALEUR -1 POUR NEXT FIND SUBBLOCK LEXER");
			}
			
			System.out.println("I="+i);
			System.out.println("NEXT="+next);
			System.out.println("MAX="+(this.tokenBlock.getBlock().size() - 1));
			
			if(next == (this.tokenBlock.getBlock().size() - 1)) {
				System.out.println("break.");
				break;
			}else {
				System.out.println("continue.");
				
				String nextsubline = this.tokenBlock.getBlock().get(next);
				
				System.out.println("nextsubline ? -> " + nextsubline);
				
				if(this.isSubBlockBody(nextsubline)) {
					i = next - 1;
					System.out.println("subblock patch next - 1");
				}else {
					i = next;
				}
			}
		}
	}
	
	private int getSubBlock(int start, boolean isBody) {
		System.out.println("-- SUBBLOCK SEARCH (start="+start+", isBody: "+isBody+")");
		
		int x = 0, y = 0;
		
		boolean check = isBody;
		
		for(int i = start ; i < this.tokenBlock.getBlock().size() ; i++) {
			String line = this.tokenBlock.getBlock().get(i);
			
			System.out.println("subline -> "+line+" at line "+i);
			
			if(check) {
				line = line.substring(1);
				check = false;
				
				System.out.println("subline (edit - body start) -> "+line+" at line "+i);
			}
			
			for(char c : line.toCharArray()) {
				if(!(c == '{' || c == '}')) {
					continue;
				}
				
				if(c == '{') {
					x++;
				}
				
				System.out.println("subblock finder1 x: "+x+", y:"+y);
				
				if(x == y) {
					return i;
				}
				
				if(c == '}') {
					y++;
				}
				
				System.out.println("subblock finder2 x: "+x+", y:"+y);
				
				if(x == y) {
					System.out.println("retour subblock correct");
					
					return i;
				}
			}
		}
		
		System.out.println("RETOUR ERREUR !");
		
		return -1;
	}
	
	private Map<Integer, String> getBlockLines(int start, int end) {
		Map<Integer, String> code = new HashMap<>();
		
		int c = 0;
		
		for(int i = start ; i <= end ; i++) {
			System.out.println("getblockline -> "+this.tokenBlock.getBlock().get(i));
			code.put(c, this.tokenBlock.getBlock().get(i));
			c++;
		}
		
		return code;
	}
	
	private boolean hasMoreBlocks() {
		System.out.println("hasMore token block block -> "+this.tokenBlock.getBlock().toString());
		System.out.println("size tokenblock -> "+this.tokenBlock.getBlock().size());
		
		for(int i = 0 ; i < this.tokenBlock.getBlock().size() ; i++) {
			String lines = this.tokenBlock.getBlock().get(i);
			
			System.out.println("has more blocks -> " + lines);
			
			if(this.isBlockOrSubBlock(lines)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isBlockOrSubBlock(String text) {
		return isBlock(text) || isSubBlockBody(text);
	}
	
	public boolean isBlock(String text) {
		return text.contains("{") || text.contains("}");
	}
	
	public boolean isSubBlockBody(String text) {
		return text.startsWith("} " + Words.ELSE) || text.startsWith("}" + Words.ELSE) ||
				text.startsWith("} " + Words.ELSEIF) || text.startsWith("}" + Words.ELSEIF);
	}
	
	public TokenBlock getFinalToken() {
		return this.tokenBlock;
	}
}

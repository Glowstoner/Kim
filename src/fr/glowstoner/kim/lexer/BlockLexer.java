package fr.glowstoner.kim.lexer;

import java.util.ArrayList;
import java.util.List;

public class BlockLexer {

	private TokenBlock tokenBlock;
	
	public BlockLexer(TokenBlock tokenBlock) {
		this.tokenBlock = tokenBlock;
		
		System.out.println("verif string tokenblock");
		System.out.println(this.tokenBlock.getBlock().toString());
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
			
			this.tokenBlock.addSubBlock(new TokenBlock(this.tokenBlock,
					this.getBlockLines(i + 1, next - 1), i + 1, next - 1));
			
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
				
				//patch subblock missing
				String nextsubline = this.tokenBlock.getBlock().get(next);
				
				System.out.println("nextsubline ? -> " + nextsubline);
				
				if(this.isSubBlockBody(nextsubline)) {
					i = next - 1;
					System.out.println("subblock patch next - 1");
				}else {
					i = next;
				}
				//end patch
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
	
	private List<String> getBlockLines(int start, int end) {
		List<String> list = new ArrayList<>();
		
		for(int i = start ; i <= end ; i++) {
			list.add(this.tokenBlock.getBlock().get(i));
		}
		
		return list;
	}
	
	private boolean hasMoreBlocks() {
		for(String lines : this.tokenBlock.getBlock()) {
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
		return text.startsWith("} sinon") || text.startsWith("}sinon") ||
				text.startsWith("} sinon si") || text.startsWith("}sinon si");
	}
	
	public TokenBlock getFinalToken() {
		return this.tokenBlock;
	}
}

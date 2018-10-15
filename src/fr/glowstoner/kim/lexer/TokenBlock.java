package fr.glowstoner.kim.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.glowstoner.kim.util.search.Recursive;

public class TokenBlock extends Token implements Recursive<TokenBlock>{
	
	private Map<Integer, String> block = new HashMap<>();
	private List<TokenBlock> subBlocks = new ArrayList<>();
	private TokenBlock parent;
	private int start, end;
	
	public TokenBlock(TokenBlock parent, Map<Integer, String> block, int start, int end) {
		super(TokenType.BLOCK);
		
		this.setBlock(block);
		this.setEnd(end);
		this.setStart(start);
		this.setParent(parent);
	}
	
	public Map<Integer, String> getBlock() {
		return this.block;
	}
	
	public void setBlock(Map<Integer, String> block) {
		this.block = block;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
	public List<TokenBlock> getSubBlocks() {
		return this.subBlocks;
	}
	
	public void setSubBlocks(List<TokenBlock> subBlocks) {
		this.subBlocks = subBlocks;
	}
	
	public void addSubBlock(TokenBlock subBlock) {
		this.subBlocks.add(subBlock);
		
		subBlock.setParent(this);
	}

	public TokenBlock getParent() {
		return parent;
	}

	public void setParent(TokenBlock parent) {
		this.parent = parent;
	}

	@Override
	public List<TokenBlock> childs() {
		return this.subBlocks;
	}

	@Override
	public TokenBlock parent() {
		return this.parent;
	}
	
	@Override
	public String toString() {
		return "{block: "+this.block.toString()+", start: "+(this.start + 1)+", end: "+(this.end + 1)
				+ ", subBlocks:"+this.subBlocks.toString()+"}";
	}
}

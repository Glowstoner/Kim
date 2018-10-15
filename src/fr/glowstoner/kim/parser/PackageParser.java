package fr.glowstoner.kim.parser;

import fr.glowstoner.kim.Words;
import fr.glowstoner.kim.executor.PackageExecutor;
import fr.glowstoner.kim.executor.PackageInfo;
import fr.glowstoner.kim.lexer.InstructionToken;
import fr.glowstoner.kim.lexer.TokenLineType;

public class PackageParser implements Parser<PackageExecutor>{

	@Override
	public boolean canParse(InstructionToken token) {
		if(token.getFirstToken().getType().equals(TokenLineType.KEYWORDS) &&
				token.getFirstToken().getToken().equals(Words.DEC)) {
			
			if(token.getTokens().get(1).getType().
					equals(TokenLineType.IDENTIFIERS)) {
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public PackageExecutor parse(InstructionToken token) {
		return new PackageExecutor(this.parsePackageInfo(token.getBase()));
	}

	private PackageInfo parsePackageInfo(String text) {
		PackageInfo pi = new PackageInfo();
		
		int c = text.length() - text.replaceAll(".", "").length();
		
		if(c < 2) {
			throw new Error("Déclaration mal formée ! Moins de deux arguments !");
		}

		String[] parts = text.split(".");
		
		pi.setDomain(parts[0]);
		pi.setAuthor(parts[1]);
		
		if(c == 2) {
			pi.setName("default");
		}else {
			StringBuilder builder = new StringBuilder();
			
			for(int i = 2 ; i < parts.length ; i++) {
				builder.append(parts[i] + ".");
			}
			
			pi.setName(builder.toString());
		}
		
		return pi;
	}
}

package fr.glowstoner.kim.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fr.glowstoner.kim.lexer.Lexer;

public class FileReader {
	
	private List<String> lines = new ArrayList<>();
	private File file;
	
	public FileReader(File file) {
		this.file = file;
	}

	public void run() {
		this.read();
		
		Lexer lexer = new Lexer(this.lines);
		lexer.registerTokens();
		lexer.parseBlocks();
		lexer.showTokens();
	}
	
	private void read() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.file), "UTF-8"));
			
			br.lines().forEach(this.lines::add);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FileReader fr = new FileReader(new File("test.kim"));
		fr.run();
	}
}
package fr.glowstoner.kim.executor;

public class PrintExecutor implements Executor{

	private String text;
	
	public PrintExecutor(String text) {
		this.setText(text);
	}

	@Override
	public void execute() {
		System.out.println(text);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

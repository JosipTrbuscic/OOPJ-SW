package hr.fer.zemris.java.p12;

public class Poll {
	private int id;
	private String title;
	private String message;
	
	public Poll(int id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}
}

package hr.fer.zemris.java.hw05.db;

import javax.net.ssl.SSLContext;

public class QueryLexer {
	private char[] data;
	private int currentIndex;
	
	public QueryLexer(String query) {
		data = query.toCharArray();
		currentIndex = 0;
	}
	
	public String getNextAttributeName() {
		skipBlanks();
		int startIndex = currentIndex;
		
		while(currentIndex < data.length) {
			if(!Character.isLetter(data[currentIndex])) {
				break;
			}
			currentIndex++;
		}
		int endIndex = currentIndex;
		//DODAJ IZNIMKU
		if(currentIndex == data.length) throw new IllegalArgumentException();
		
		return new String(data, startIndex, endIndex-startIndex);
	}
	
	public String getNextOperator() {
		skipBlanks();
		int startIndex = currentIndex;
		
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex]) || data[currentIndex] == '"') {
				break;
			}
			currentIndex++;
		}
		
		int endIndex = currentIndex;
		//DODAJ IZNIMKU
		if(currentIndex == data.length) throw new IllegalArgumentException();
		
		return new String(data, startIndex, endIndex-startIndex);
	}
	
	public String getNextLiteral() {
		skipBlanks();
		int startIndex = ++currentIndex;
		
		while(currentIndex < data.length) {
			if(data[currentIndex] == '"') {
				break;
			}
			currentIndex++;
		}
		
		int endIndex = currentIndex;
		//DODAJ IZNIMKU
		if(currentIndex == data.length) throw new IllegalArgumentException();
		currentIndex++;
		return new String(data, startIndex, endIndex-startIndex);
	}
	
	public String nextLogicOperator() {
		skipBlanks();
		
		int startIndex = currentIndex;
		
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex])) {
				break;
			}
			currentIndex++;
		}
		
		int endIndex = currentIndex;
		//DODAJ IZNIMKU
		if(currentIndex == data.length) throw new IllegalArgumentException();
		
		return new String(data, startIndex, endIndex-startIndex);
	}
	
	public boolean hasNext() {
		skipBlanks();
		
		return currentIndex < data.length;
	}
	
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == '\r' || c == '\n' || c == '\t' || c == ' ')
				currentIndex++;
			else
				break;
		}
	}
	
}

package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class StudentDB {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<String> lines = null;
		StudentDatabase db;
		
		try {
			lines = Files.readAllLines(
				    Paths.get("./src/test/resources/database.txt"), 
				    StandardCharsets.UTF_8
				   );
		} catch (IOException e) {
			System.out.println("Cant load test file");
		}
		
		db= new StudentDatabase(lines);
		
		while(true) {
			System.out.print("> ");
			StringBuilder sb = new StringBuilder();
			
			if(sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
			
			if(sb.toString().equals("exit")) {
				System.exit(0);
			}
			
			if(sb.toString().trim().startsWith("query")) {
				QueryParser parser = new QueryParser(sb.toString().replace("query", "").trim());
				List<StudentRecord> records  = db.filter(new QueryFilter(parser.getQuery()));
				
				printResults(records);
				
			}else {
				throw new IllegalArgumentException("Invalid query keyword");
			}
		}
	}
	
	
	public static void printResults(List<StudentRecord> records) {
		int longestRecordLength = 0;
		int longestLastName = 0;
		int longestFirstName = 0;
		
		for (StudentRecord record: records) {
			int firstNameLength = record.getFirstName().length();
			int lastNameLength = record.getLastName().length();
			int recordLength = firstNameLength + lastNameLength;
			
			if( recordLength > longestRecordLength) {
				longestRecordLength = recordLength;
			}
			
			if(firstNameLength > longestFirstName) longestFirstName = firstNameLength;
			if(lastNameLength > longestLastName) longestLastName = lastNameLength;
		}
		
		int columns = longestRecordLength + 3+12+4+5;
		for(int i = 0;i<columns+1;++i) {
			if(isColumnSeparator(i, longestFirstName, longestLastName)) {
				System.out.print("+");
			}else {
				System.out.print("=");
			}
		}
		System.out.print("\n");
		for (StudentRecord record: records) {
			String lastName = addWhiteSpaces(record.getLastName(), longestLastName);
			String firstName = addWhiteSpaces(record.getFirstName(), longestFirstName);
			System.out.println("| "+record.getJmbag()+" | "+lastName+" | "+firstName+" | "+record.getFinalGrade() + " |");
		}
		for(int i = 0;i<columns+1;++i) {
			if(isColumnSeparator(i, longestFirstName, longestLastName)) {
				System.out.print("+");
			}else {
				System.out.print("=");
			}
		}
		System.out.print("\n");
	}
	
	public static boolean isColumnSeparator(int j, int firstName, int lastName) {
		if(j == 0 || j == 13 || j == (16+lastName) || j == (19+lastName+firstName) || j == (23+lastName+firstName)) {
			return true;
		}
		
		return false;
	}
	public static String addWhiteSpaces(String s, int maxLen) {
		StringBuilder sb = new StringBuilder(s);
		for(int i = s.length();i<maxLen;i++) {
			sb.append(" ");
		}
		return sb.toString();
	}
}
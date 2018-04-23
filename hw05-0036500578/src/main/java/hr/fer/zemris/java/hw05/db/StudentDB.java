package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing simple database management system emulator.
 * It allows user to load file which contains student records and
 * filter the database records by writing simple queries. 
 * @author Josip Trbuscic
 */
public class StudentDB {
	
	/**
	 * Length of JMBAG
	 */
	public static final int JMBAG_LENGTH = 10;
	
	/**
	 * Length of final grade
	 */
	public static final int FINAL_GRADE_LENGTH = 1;
	
	/**
	 * Main method of database management system emulator.
	 * @param args - arguments from command line.
	 * 				Not used in this method.
	 */
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
				System.out.println("Goodbye!");
				sc.close();
				System.exit(0);
			}
			
			if(sb.toString().trim().startsWith("query")) {
				try {
					QueryParser parser = new QueryParser(sb.toString().replace("query", "").trim());
					List<StudentRecord> records  = new LinkedList<>();
					
					if(parser.isDirectQuery()) {
						  StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
						  records.add(r);
						  System.out.println("Using index for record retrieval");
					} else {
						  for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
							  records.add(r);
						  }
					}
					if(records.size() != 0) {
						printResults(records);
					}
					System.out.println("Records selected: "+records.size());
				}catch(QueryParserException ex) {
					System.out.println("Invalid query");
				}
				
			}else {
				System.out.println("Invalid query keyword");
			}
		}
	}
	
	/**
	 * Prints elements of the given list to standard output
	 * @param records - list to be printed
	 */
	public static void printResults(List<StudentRecord> records) {
		int longestLastName = 0;
		int longestFirstName = 0;
		
		//find longest first name and last name
		for (StudentRecord record: records) {
			int firstNameLength = record.getFirstName().length();
			int lastNameLength = record.getLastName().length();
			
			if(firstNameLength > longestFirstName) longestFirstName = firstNameLength;
			if(lastNameLength > longestLastName) longestLastName = lastNameLength;
		}
		
		int[] attributesLength = new int[4];
		attributesLength[0] = JMBAG_LENGTH;
		attributesLength[1] = longestLastName;
		attributesLength[2] = longestFirstName;
		attributesLength[3] = FINAL_GRADE_LENGTH;
		
		printFrame(attributesLength);
		
		for (StudentRecord record: records) {
			String lastName = addWhiteSpaces(record.getLastName(), longestLastName);
			String firstName = addWhiteSpaces(record.getFirstName(), longestFirstName);
			System.out.println("| "+record.getJmbag()+" | "+lastName+" | "+firstName+" | "+record.getFinalGrade() + " |");
		}
		
		printFrame(attributesLength);
	}
	
	/**
	 * Adds whitespaces to the given string
	 * until max length is reached.
	 * @param s - original string
	 * @param maxLen - max length of string
	 * @return new string with added whitespaces
	 */
	private static String addWhiteSpaces(String s, int maxLen) {
		StringBuilder sb = new StringBuilder(s);
		for(int i = s.length();i<maxLen;i++) {
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * Method prints frame of the table based on the length
	 * of each attribute
	 * @param attributesLength - lengths of each attribute
	 */
	private static void printFrame(int[] attributesLength) {
		for (Integer length : attributesLength) {
			System.out.print('+');
			for (int i = 0; i < length + 2; i++) {
				System.out.print('=');
			}

		}
		System.out.print('+');
		System.out.print('\n');
}
}
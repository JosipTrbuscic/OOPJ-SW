package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demonstration program of stream java API performed 
 * on student database
 * @author Josip Trbuscic
 *
 */
public class StudentDemo {
	/**
	 * Method where program starts
	 * @param args -command line arguments. Not used here
	 */
	public static void main(String[] args) {
		
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(
				    Paths.get("./src/main/resources/studenti.txt"), 
				    StandardCharsets.UTF_8
				   );
		} catch (IOException e) {
			System.out.println("Cant load test file");
		}
		
		List<StudentRecord> records = convert(lines);
		//---------------------------------------------------------------------------------
		long broj = vratiBodovaViseOd25(records);
		System.out.println(broj);
		System.out.println("\n");
		//---------------------------------------------------------------------------------
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println(broj5);
		System.out.println("\n");
		//---------------------------------------------------------------------------------
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		for(StudentRecord student: odlikasi) {
			System.out.println(student.getJmbag());
		}
		System.out.println("\n");
		//---------------------------------------------------------------------------------
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		for(StudentRecord student: odlikasiSortirano) {
			System.out.println(student.getJmbag());
		}
		System.out.println("\n");
		//---------------------------------------------------------------------------------
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		for(String student: nepolozeniJMBAGovi) {
			System.out.println(student);
		}
		System.out.println("Broj Nepoloženih"+nepolozeniJMBAGovi.size());
		System.out.println("\n");
		//---------------------------------------------------------------------------------
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		for(int ocjena: mapaPoOcjenama.keySet()) {
			for(StudentRecord student : mapaPoOcjenama.get(ocjena)) {
				System.out.println(student.getJmbag()+ " -> "+ocjena);
			}
		}
		System.out.println("\n");
		//---------------------------------------------------------------------------------
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		for(int ocjena: mapaPoOcjenama2.keySet()) {
			System.out.println(ocjena + " -> "+mapaPoOcjenama2.get(ocjena));
		}
		System.out.println("\n");
		//---------------------------------------------------------------------------------
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		for(boolean prolaz: prolazNeprolaz.keySet()) {
			for(StudentRecord student : prolazNeprolaz.get(prolaz)) {
				System.out.println(prolaz+ " -> "+student.getJmbag());
			}
		}
		System.out.println("\n");
	}
	
	/**
	 * Returns list of students whose score sum is bigger
	 * than 25
	 * @param records - list of student records
	 * @return list of students whose score sum is bigger
	 * than 25
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().
						filter(r->{return r.getLabScore() + r.getMiScore() + r.getZiScore() > 25;}).
						count(); 
	}
	
	/**
	 * Returns number of students whose final grade is 5
	 * @param records - list of student records
	 * @return number of students whose final grade is 5
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().
				filter(r->r.getFinalGrade() == 5).
				count();
	}
	
	/**
	 * Returns list of students whose final grade is 5
	 * @param records - list of student records
	 * @return list of students whose final grade is 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().
						filter(r->r.getFinalGrade() == 5).
						collect(Collectors.toList()); 
	}
	
	/**
	 * Returns sorted list of students whose final grade is 5. 
	 * Student are sorted in descending order by their score
	 * @param records - list of student records
	 * @return sorted list of students whose final grade is 5
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().
						filter(r->r.getFinalGrade() == 5).
						sorted((r1,r2)->{return  Double.compare(
													 r2.getLabScore() + r2.getMiScore() + r2.getZiScore(),
													r1.getLabScore() + r1.getMiScore() + r1.getZiScore());
										}).
						collect(Collectors.toList()); 
	}
	
	/**
	 * Returns list of students whose final grade is 5
	 * @param records - list of student records
	 * @return list of students whose final grade is 5
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().
						filter(r->r.getFinalGrade() == 1).
						map(r1->r1.getJmbag()).
						sorted().
						collect(Collectors.toList());
	}
	
	/**
	 * Returns map of students grouped by their final grade
	 * @param records - list of student records
	 * @return map of students grouped by their final grade
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().
				collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}
	
	/**
	 * Returns map of numbers of students with the specific
	 * final grade
	 * @param records - list of student records
	 * @return map of number of student with the specific
	 * 			final grade
	 */
	private static Map<Integer, Integer>  vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().
				collect(Collectors.toMap(StudentRecord::getFinalGrade,
											r->1,
											(r1,r2) -> r1+r2));
	}
	
	/**
	 * Returns map of students grouped in two groups, 
	 * group which failed the class and group of
	 * students who passed
	 * @param records - list of student records
	 * @return  map of students grouped in two groups, 
	 * 			group which failed the class and group of
	 * 			students who passed
	 */
	private static Map<Boolean, List<StudentRecord>>  razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().
				collect(Collectors.partitioningBy(r->r.getFinalGrade() > 1));
	}
	
	/**
	 * Creates list of student records from given list of strings
	 * which contain student info
	 * @param recordStrings - strings of student info
	 * @return list of student records
	 */
	private static List<StudentRecord> convert(List<String> recordStrings){
		List<StudentRecord> records = new LinkedList<>();
		
		for(String student : recordStrings) {
			records.add(recordFactory(student));
		}
		
		return records;
	}
	
	/**
	 * parses string as student record and returns
	 * new instance of {@link StudentRecord} class
	 * @param record - string representation of student record
	 * @return instance of {@link StudentRecord} represented by given string
	 */
	private static StudentRecord recordFactory(String record) {
		String[] parts = record.split("\\t+");
		return new StudentRecord(parts[0], parts[1], parts[2], parts[3],
								parts[4], parts[5], parts[6]);
	}
}

package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * This class is representation of database which stores student records. 
 * Records are stored as instances of {@link StudentRecord} class in a 
 * {@link SimpleHashtable}. Each student record will be created from 
 * formated string. Student has a unique 10 digit JMBAG which is used as a key.
 * Database offers filtering student records where filter is given as sequence
 * of conditional expressions.
 * @author Josip Trbuscic
 *
 */
public class StudentDatabase {
	/**
	 * List of student records
	 */
	private List<StudentRecord> recordList;
	
	/**
	 * Map of student records
	 */
	private SimpleHashtable<String, StudentRecord> recordMap;
	
	/**
	 * Constructs new database from given list of strings where 
	 * each string contains all info needed to create student record
	 * @param records - list of student records 
	 * @throws NullPointerException if given list is null
	 */
	public StudentDatabase(List<String> records) {
		if(records == null) throw new NullPointerException("Records cannot be null");
		
		this.recordList = new LinkedList<StudentRecord>();
		this.recordMap = new SimpleHashtable<String, StudentRecord>(records.size());
		fillMapAndList(records);
	}
	
	/**
	 * Returns student record from given JMBAG in 
	 * constant time complexity if such student exists.
	 * @param jmbag - to search for
	 * @return Student record if it exists in a map
	 * @throws NullPointerException if given string is null
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return recordMap.get(jmbag);
	}
	
	/**
	 * Filters the student database by testing every
	 * student record
	 * @param filter - student record filter
	 * @return list containing student records which
	 * 			have passed through filter
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> accepted = new LinkedList<>();
		
		for(StudentRecord record:recordList) {
			if(filter.accepts(record)) {
				accepted.add(record);
			}
		}
		
		return accepted;
	}
	
	/**
	 * Parses each string from given list 
	 * as student record and adds it to 
	 * the map and the list
	 * @param records - list of strings 
	 */
	private void fillMapAndList(List<String> records) {
		for(String recordString: records) {
			StudentRecord record = recordFactory(recordString);
			
			recordMap.put(record.getJmbag(), record);
			recordList.add(record);
		}
		
	}
	
	/**
	 * parses string as student record and returns
	 * new instance of {@link StudentRecord} class
	 * @param record - string representation of student record
	 * @return instance of {@link StudentRecord} represented by given string
	 */
	private StudentRecord recordFactory(String record) {
		String[] parts = record.split("\\t+");
		return new StudentRecord(parts[0], parts[1], parts[2], parts[3]);
	}

	/**
	 * Return list of student records
	 * @return list of student records
	 */
	public List<StudentRecord> getRecordList() {
		return recordList;
	}

	/**
	 * Returns map of student records
	 * @return map of student records
	 */
	public SimpleHashtable<String, StudentRecord> getRecordMap() {
		return recordMap;
	}
}

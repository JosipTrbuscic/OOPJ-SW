package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

public class StudentDatabase {
	private List<StudentRecord> recordList;
	private SimpleHashtable<String, StudentRecord> recordMap;
	
	public StudentDatabase(List<String> records) {
		this.recordList = new LinkedList<StudentRecord>();
		this.recordMap = new SimpleHashtable<String, StudentRecord>(records.size());
		fillMapAndList(records);
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		return recordMap.get(jmbag);
	}
	
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> accepted = new LinkedList<>();
		
		for(StudentRecord record:recordList) {
			if(filter.accepts(record)) {
				accepted.add(record);
			}
		}
		
		return accepted;
	}
	
	private void fillMapAndList(List<String> records) {
		for(String recordString: records) {
			StudentRecord record = recordFactory(recordString);
			
			recordMap.put(record.getJmbag(), record);
			recordList.add(record);
		}
		
	}
	
	private StudentRecord recordFactory(String record) {
		String[] parts = record.split("\\t+");
		return new StudentRecord(parts[0], parts[1], parts[2], parts[3]);
	}

	public List<StudentRecord> getRecordList() {
		return recordList;
	}

	public SimpleHashtable<String, StudentRecord> getRecordMap() {
		return recordMap;
	}
}

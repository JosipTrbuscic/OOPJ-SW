package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudentDatabaseTest {
	public List<String> lines;
	public StudentDatabase db;
	
	
	@Before
	public void setup() {
		try {
			lines = Files.readAllLines(
				    Paths.get("./src/test/resources/database.txt"), 
				    StandardCharsets.UTF_8
				   );
		} catch (IOException e) {
			System.out.println("Cant load test file");
		}
		
		db= new StudentDatabase(lines);
	}

	@Test
	public void forJMBAGExisting() {
		StudentRecord first = db.forJMBAG("0000000006");
		StudentRecord second = db.forJMBAG("0000000014");
		StudentRecord third = db.forJMBAG("0000000009");
		
		assertEquals("Cvrlje", first.getLastName());
		assertEquals("Ivan", first.getFirstName());
		assertEquals("3", first.getFinalGrade());
		
		assertEquals("Gašić", second.getLastName());
		assertEquals("Mirta", second.getFirstName());
		assertEquals("3", second.getFinalGrade());
		
		assertEquals("Dean", third.getLastName());
		assertEquals("Nataša", third.getFirstName());
		assertEquals("2", third.getFinalGrade());
	}
	
	@Test
	public void forJMBAGNotExisting() {
		StudentRecord first = db.forJMBAG("00000007");
		StudentRecord second = db.forJMBAG("000000064");
		StudentRecord third = db.forJMBAG("0000000100");
		
		assertEquals(null, first);
		assertEquals(null, second);
		assertEquals(null, third);
	}
	
	@Test
	public void forJMBAGNull() {
		StudentRecord first = db.forJMBAG(null);
		assertEquals(null, first);
	}
	
	@Test
	public void filterAlwaysTrue() {
		List<StudentRecord> allRecords;

		allRecords = db.filter((StudentRecord record) -> {
			return true;
		});
		
		assertEquals(db.getRecordList().size(), allRecords.size());
	}
	
	@Test
	public void filterAlwaysFalse() {
		List<StudentRecord> allRecords;

		allRecords = db.filter((StudentRecord record) -> {
			return false;
		});
		
		assertEquals(0, allRecords.size());
	}

}

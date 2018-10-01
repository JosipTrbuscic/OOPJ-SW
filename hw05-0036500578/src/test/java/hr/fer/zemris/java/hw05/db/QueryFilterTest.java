package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class QueryFilterTest {
	List<String> lines = null;
	StudentDatabase db;
	
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
	public void directQueryFilter() {
		QueryParser parser = new QueryParser("jmbag=\"0000000001\"");
		if(parser.isDirectQuery()) {
			  assertEquals("0000000001",db.forJMBAG(parser.getQueriedJMBAG()).getJmbag());
		}
	}
	
	@Test
	public void jmbagFilter() {
		QueryParser parser = new QueryParser("jmbag>\"0000000050\"");
		assertEquals(13, db.filter(new QueryFilter(parser.getQuery())).size());
		
		QueryParser parser1 = new QueryParser("jmbag>\"0000000050\" and jmbag<\"0000000050\"");
		assertEquals(0, db.filter(new QueryFilter(parser1.getQuery())).size());
		
		QueryParser parser2 = new QueryParser("jmbagLIKE\"00000000*\"");
		assertEquals(63, db.filter(new QueryFilter(parser2.getQuery())).size());
	}
	
	@Test
	public void lastNameFilter() {
		QueryParser parser = new QueryParser("lastName>\"A\"");
		assertEquals(63, db.filter(new QueryFilter(parser.getQuery())).size());
		
		QueryParser parser2 = new QueryParser("lastNameLIKE\"A*\" and lastNameLIKE\"B*\"");
		assertEquals(0, db.filter(new QueryFilter(parser2.getQuery())).size());
		
		QueryParser parser3 = new QueryParser("lastName<\"C\"");
		assertEquals(5, db.filter(new QueryFilter(parser3.getQuery())).size());
	}
	
	@Test
	public void firstNameFilter() {
		QueryParser parser = new QueryParser("firstName>\"A\"");
		assertEquals(63, db.filter(new QueryFilter(parser.getQuery())).size());
		
		QueryParser parser4 = new QueryParser("firstName>=\"A\"");
		assertEquals(63, db.filter(new QueryFilter(parser4.getQuery())).size());
		
		QueryParser parser2 = new QueryParser("firstNameLIKE\"Br*\" and firstNameLIKE\"Z*\"");
		assertEquals(0, db.filter(new QueryFilter(parser2.getQuery())).size());
		
		QueryParser parser3 = new QueryParser("firstName<\"C\"");
		assertEquals(10, db.filter(new QueryFilter(parser3.getQuery())).size());
	}
	
	@Test
	public void combinedFilter() {
		QueryParser parser = new QueryParser("firstName>\"A\" and jmbag>=\"0000000009\"");
		assertEquals(55, db.filter(new QueryFilter(parser.getQuery())).size());
		
		QueryParser parser4 = new QueryParser("firstName=\"Irena\" and jmbag=\"0000000060\" and lastName=\"Vignjević\"");
		assertEquals(1, db.filter(new QueryFilter(parser4.getQuery())).size());
	}

	
}

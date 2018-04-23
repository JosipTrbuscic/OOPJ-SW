package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class FieldValueGettersTest {
	List<StudentRecord> list;
	
	@Before
	public void setup() {
		list = new ArrayList<>();
		list.add(new StudentRecord("0000000001",	"Akšamović",	"Marin",	"2"));
		list.add(new StudentRecord("0000000002",	"Bakamović",	"Petra",	"2"));
		list.add(new StudentRecord("0000000005",	"Jurina",	"Filip",	"2"));
		
	}
	
	@Test
	public void firstNameGetter() {
		assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(list.get(0)));
		assertEquals("Petra", FieldValueGetters.FIRST_NAME.get(list.get(1)));
		assertEquals("Filip", FieldValueGetters.FIRST_NAME.get(list.get(2)));
	}
	
	@Test(expected=NullPointerException.class)
	public void firstNameGetterNull() {
		assertEquals("test", FieldValueGetters.FIRST_NAME.get(null));
	}
	
	@Test
	public void lastNameGetter() {
		assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(list.get(0)));
		assertEquals("Bakamović", FieldValueGetters.LAST_NAME.get(list.get(1)));
		assertEquals("Jurina", FieldValueGetters.LAST_NAME.get(list.get(2)));
	}
	
	@Test(expected=NullPointerException.class)
	public void lastNameGetterNull() {
		assertEquals("test", FieldValueGetters.LAST_NAME.get(null));
	}
	
	@Test
	public void jmbagGetter() {
		assertEquals("0000000001", FieldValueGetters.JMBAG.get(list.get(0)));
		assertEquals("0000000002", FieldValueGetters.JMBAG.get(list.get(1)));
		assertEquals("0000000005", FieldValueGetters.JMBAG.get(list.get(2)));
	}
	
	@Test(expected=NullPointerException.class)
	public void jmbagGetterNull() {
		assertEquals("test", FieldValueGetters.JMBAG.get(null));
	}

}

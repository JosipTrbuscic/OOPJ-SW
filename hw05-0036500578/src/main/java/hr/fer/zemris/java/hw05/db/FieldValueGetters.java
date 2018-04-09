package hr.fer.zemris.java.hw05.db;

public class FieldValueGetters {
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();
	
	public static void main(String[] args) {
		StudentRecord record = new StudentRecord("0036500578", "Trbuscic", "Josip", "1");
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
	}
	
}

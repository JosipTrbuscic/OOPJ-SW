package hr.fer.zemris.java.hw05.db;

/**
 * This class offers static variables used
 * for retrieving specific attribute of 
 * a {@link StudentRecord} 
 * @author Josip Trbuscic
 */
public class FieldValueGetters {
	
	/**
	 * First name attribute of a student record
	 */
	public static final IFieldValueGetter FIRST_NAME = record ->{
		if(record == null) throw new NullPointerException("Record cannot be null");
		return record.getFirstName();
	};
	
	/**
	 * Last name attribute of a student record
	 */
	public static final IFieldValueGetter LAST_NAME = record ->{
		if(record == null) throw new NullPointerException("Record cannot be null");
		return record.getLastName();
	};
	
	/**
	 * JMBAG attribute of a student record
	 */
	public static final IFieldValueGetter JMBAG = record ->{
		if(record == null) throw new NullPointerException("Record cannot be null");
		return record.getJmbag();
	};
	
	/**
	 * Tester method
	 * @param args
	 */
	public static void main(String[] args) {
		StudentRecord record = new StudentRecord("0036500578", "Trbuscic", "Josip", "1");
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
	}
	
}

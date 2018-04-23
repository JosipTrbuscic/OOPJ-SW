package hr.fer.zemris.java.hw05.db;

/**
 * This class represents each student in database. 
 * Each record contains JMBAG which is unique for each record,
 * first and last name and final grade of a student. 
 * @author Josip Trbuscic
 */
public class StudentRecord {
	
	/**
	 * Student's JMBAG
	 */
	private String jmbag;
	
	/**
	 * Student's last name
	 */
	private String lastName;
	
	/**
	 * Student's first name
	 */
	private String firstName;
	
	/**
	 * Student's final grade
	 */
	private String finalGrade;
	
	
	/**
	 * Constructs new student record with given parameters. None 
	 * of the parameters can be null.
	 * @param jmbag - student's JMBAG
	 * @param lastName - student's last name
	 * @param firstName - student's first name
	 * @param finalGrade - student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		if(lastName == null) throw new NullPointerException("Last name cannot be null");
		if(firstName == null) throw new NullPointerException("First name cannot be null");
		if(jmbag == null) throw new NullPointerException("JMBAG cannot be null");
		if(finalGrade == null) throw new NullPointerException("Final grade cannot be null");
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns hash value of a student.
	 * Hash value is based of a JMBAG attribute
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	
	/**
	 * Determines if some other object is equal to this one 
	 * @param obj - other object
	 * @return {@code true} if other object is equal to this one,
	 * 			false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Returns student's JMBAG
	 * @return student's JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student's last name
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student's first name
	 * @return student's JMBAG
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns student's final grade
	 * @return student's final grade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}
	
	
}

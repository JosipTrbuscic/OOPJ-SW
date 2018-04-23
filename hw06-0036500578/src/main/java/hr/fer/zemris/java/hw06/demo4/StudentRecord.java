package hr.fer.zemris.java.hw06.demo4;

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
	private int finalGrade;
	
	private double miScore;
	
	private double ziScore;
	
	private double labScore;
	
	
	/**
	 * Constructs new student record with given parameters. None 
	 * of the parameters can be null.
	 * @param jmbag - student's JMBAG
	 * @param lastName - student's last name
	 * @param firstName - student's first name
	 * @param miScore - student's MI score
	 * @param ziScore - student's ZI score
	 * @param labScore - student's LAB score
	 * @param finalGrade - student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String miScore,
						String ziScore, String labScore, String finalGrade) {
		if(lastName == null) throw new NullPointerException("Last name cannot be null");
		if(firstName == null) throw new NullPointerException("First name cannot be null");
		if(jmbag == null) throw new NullPointerException("JMBAG cannot be null");
		if(finalGrade == null) throw new NullPointerException("Final grade cannot be null");
		if(ziScore == null) throw new NullPointerException("ZI score cannot be null");
		if(miScore == null) throw new NullPointerException("MI score cannot be null");
		if(labScore == null) throw new NullPointerException("LAB score cannot be null");
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = Integer.valueOf(finalGrade);
		this.miScore = Double.valueOf(miScore);
		this.ziScore = Double.valueOf(ziScore);
		this.labScore = Double.valueOf(labScore);
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
	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * Returns student's MI score
	 * @return student's MI score
	 */
	public double getMiScore() {
		return miScore;
	}
	
	/**
	 * Returns student's ZI score
	 * @return student's ZI score
	 */
	public double getZiScore() {
		return ziScore;
	}
	
	/**
	 * Returns student's LAB score
	 * @return student's LAB score
	 */
	public double getLabScore() {
		return labScore;
	}
	
	
}

package hr.fer.zemris.java.hw05.db;

public class StudentRecord {
	private String jmbag;
	private String lastName;
	private String firstName;
	private String finalGrade;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

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

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFinalGrade() {
		return finalGrade;
	}
	
	
}

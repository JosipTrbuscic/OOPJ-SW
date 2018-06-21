package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity representing blog user
 * @author Josip Trbuscic
 *
 */
@Entity
@Table(name="blog_users")
public class BlogUser {
	
	/**
	 * Users id
	 */
	private Long id;
	
	/**
	 * Users first name
	 */
	private String firstName;
	
	/**
	 * Users last name
	 */
	private String lastName;
	
	/**
	 * Users nick
	 */
	private String nick;
	
	/**
	 * Users email
	 */
	private String email;
	
	/**
	 * Users hashed password
	 */
	private String passwordHash;
	
	/**
	 * List of blog entries created by this user
	 */
	private List<BlogEntry> entries = new ArrayList<>();
	
	/**
	 * Returns id of the user
	 * @return id of the user
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets id of the user
	 * @param id - id of the user
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns first name of the user
	 * @return first name of the user
	 */
	@Column(length=20,nullable=false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user
	 * @param firstName - users first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Returns last name of the user
	 * @return last name of the user
	 */
	@Column(length=20,nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets last name of the user
	 * @param lastName - users last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	/**
	 * Returns users nick
	 * @return users nick
	 */
	@Column(nullable=false, length=20, unique=true)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Sets users nick
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Returns users email address
	 * @return users email address
	 */
	@Column(nullable=false, length=20, unique=true)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets users email address
	 * @param email address 
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns hashed password
	 * @return hashed password
	 */
	@Column(nullable=false, length=50)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Sets hashed password
	 * @param passwordHash - hashed password
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Returns blog entries created by this user
	 * @return blog entries created by this user
	 */
	@OneToMany(mappedBy="creator")
	public List<BlogEntry> getEntries() {
		return entries;
	}
	
	/**
	 * Sets entries of this user
	 * @param entries of this user
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}
}

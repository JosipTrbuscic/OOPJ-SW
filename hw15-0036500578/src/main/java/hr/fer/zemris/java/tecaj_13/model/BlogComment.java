package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity representing comment on a blog entry. Every blog 
 * entry can hold multiple comments
 * @author Josip Trbsucic
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Comment id
	 */
	private Long id;
	
	/**
	 * Blog entry
	 */
	private BlogEntry blogEntry;
	
	/**
	 * Email of a user who created the comment
	 */
	private String usersEMail;
	
	/**
	 * Comment
	 */
	private String message;
	
	/**
	 * Time when comment was created
	 */
	private Date postedOn;
	
	/**
	 * Returns comment id
	 * @return comment id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets comment id
	 * @param id - id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns blog entry this comment belongs to
	 * @return blog entry this comment belongs to
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets blog entry this comment belongs to
	 * @param blogEntry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns email of a user who created comment
	 * @return email of a user who created comment
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets email of a user who left comment
	 * @param usersEMail - users email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns users comment 
	 * @return users comment
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets users comment
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns time when this comment was posted
	 * @return time when this comment was posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets time this comment was posted
	 * @param postedOn
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
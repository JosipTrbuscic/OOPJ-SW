package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity representing blog entry 
 * @author Josip Trbuscic
 *
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * Entry id
	 */
	private Long id;
	
	/**
	 * Comments left on this entry
	 */
	private List<BlogComment> comments = new ArrayList<>();
	
	/**
	 * Time when this entry was created
	 */
	private Date createdAt;
	
	/**
	 * Time of last modification
	 */
	private Date lastModifiedAt;
	
	/**
	 * Title of this entry
	 */
	private String title;
	
	/**
	 * Text this entry contains
	 */
	private String text;
	
	/**
	 * User who created the entry
	 */
	private BlogUser creator;
	
	/**
	 * Returns id of the entry
	 * @return id of the entry
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id of the entry
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns list of comments 
	 * @return list of comments
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Sets list of comments
	 * @param comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Returns time when this post was created
	 * @return time when this post was created
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets time when this post was created
	 * @param createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns latest time this post was modified
	 * @return latest time this post was modified
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets latest time this post was modified
	 * @param lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Returns title of the post
	 * @return title of the post
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title of the post
	 * @param title - title of the post
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns text this post contains
	 * @return text this post contains
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	/**
	 * Sets text this post contains
	 * @param text - text this post contains
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Returns the user who created this post
	 * @return user who created this post
	 */
	@OneToOne
	public BlogUser getCreator() {
		return creator;
	}
	
	/**
	 * Sets the creator of this post
	 * @param creator - creator of this post
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
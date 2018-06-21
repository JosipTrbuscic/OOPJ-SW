package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * DAO implementation which comunicates with database
 * @author Josip Trbuscic
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getAllUsers() {
		List<BlogUser> users = JPAEMProvider.getEntityManager().createQuery("select bu from BlogUser bu")
				.getResultList();
		return users;
	}

	@Override
	public BlogUser getBlogUser(String nick, String password) throws DAOException {
		TypedQuery<BlogUser> query = JPAEMProvider.getEntityManager().createQuery(
				"select bu from BlogUser bu where nick = :nick and passwordhash = :password", BlogUser.class);

		query.setParameter("nick", nick);
		query.setParameter("password", password);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	@Override
	public BlogUser getUserFromNick(String nick) throws DAOException {
		TypedQuery<BlogUser> query = JPAEMProvider.getEntityManager().createQuery(
				"select bu from BlogUser bu where nick = :nick", BlogUser.class);

		query.setParameter("nick", nick);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void registerBlogUser(BlogUser user) {
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			em.persist(user);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		em.close();
	}

	@Override
	public List<BlogEntry> getUserBlogs(BlogUser user) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		TypedQuery<BlogEntry> query = em.createQuery("select be from BlogEntry be where creator = :creator", BlogEntry.class); 
		query.setParameter("creator", user);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void addBlogEntry(BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(entry);
		em.close();
		
	}

	@Override
	public void addBlogComment(BlogComment comment) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
		em.close();
		
	}

}
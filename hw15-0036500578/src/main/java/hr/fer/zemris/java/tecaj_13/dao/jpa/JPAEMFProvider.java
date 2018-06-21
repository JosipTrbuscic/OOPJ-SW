package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class which offers methods to get factory of entity managers
 * @author Josip Trbuscic
 *	
 */
public class JPAEMFProvider {

	/**
	 * Entity manager factory
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Returns emf 
	 * @return emf
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets emf
	 * @param emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}
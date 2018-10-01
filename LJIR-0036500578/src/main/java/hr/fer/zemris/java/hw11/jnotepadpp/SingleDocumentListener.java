package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interfaces that represents {@link SingleDocumentModel}'s listener
 * @author Josip Trbuscic
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Method called when specified documents content is changed
	 * @param model - document whose content was changed
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Method called when documents path has been changed
	 * @param model - document whose file has been changed
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}

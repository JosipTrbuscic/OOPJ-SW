package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that represents {@link MultipleDocumentModel}'s listener 
 * @author Josip Trbuscic
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Method which is called when currently display document has changed
	 * @param previousModel - previous document
	 * @param currentModel - current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Method called when new document has been added
	 * @param model - added document
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Method called when document has been removed
	 * @param model - removed document
	 */
	void documentRemoved(SingleDocumentModel model);
}

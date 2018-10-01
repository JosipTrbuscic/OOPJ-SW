package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface that represents model that can hold one or more documents 
 * @author Josip Trbuscic
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	

	/**
	 * Returns document currently displayed
	 * @return document currently displayed
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads document from given path. 
	 * @param path - document path
	 * @return loaded document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves given document to specified location. If given 
	 * path is null document will be saved to it's current path
	 * @param model - document to save
	 * @param newPath - save location
	 */

	/**
	 * Closes specified document
	 * @param model - document to close
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Registers specified listener if it wasn't already registered
	 * @param l - listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Unregisters specified listener if it was registered
	 * @param l - listener to remove
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of currently opened documents
	 * @return number of opened documents
	 */
	int getNumberOfDocuments();

	/**
	 * Returns document at specified index
	 * @param index - document index
	 * @return document from given index
	 */
	SingleDocumentModel getDocument(int index);
}

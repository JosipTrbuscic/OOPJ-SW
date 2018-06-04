package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface that represents single document
 * @author Josip Trbuscic
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Returns component on which documents content is displayed
	 * @return component on which documents content is displayed
	 */
	JTextArea getTextComponent();

	/**
	 * Returns documents path
	 * @return documents path
	 */
	Path getFilePath();

	/**
	 * Sets given path as current one
	 * @param path - path to set
	 */
	void setFilePath(Path path);

	/**
	 * Indicates if document was changed since last save
	 * @return true if document has been modified, false otherwise
	 */
	boolean isModified();

	/**
	 * Sets modification flag to given one
	 * @param modified - modification flag
	 */
	void setModified(boolean modified);

	/**
	 * Registers specified listener if it wasn't already registered
	 * @param l - listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Unregisters specified listener if it was registered
	 * @param l - listener to remove
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}

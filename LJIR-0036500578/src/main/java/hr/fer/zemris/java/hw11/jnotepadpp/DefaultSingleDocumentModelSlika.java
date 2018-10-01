package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class that represent model of single document. Class holds
 * information about file path from which document was loaded
 * document modification status and reference to Swing component
 *  which is used for editing. 
 * @author Josip Trbuscic
 *
 */
public class DefaultSingleDocumentModelSlika implements SingleDocumentModel{
	/**
	 * Document path
	 */
	private Path path;
	
	/**
	 * Flag which indicates if document was edited
	 */
	private boolean modified = false;
	
	/**
	 * Component used for editing document
	 */
	private Slika slika;
	
	private JTable tablica;
	
	/**
	 * Listeners
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Constructor
	 * @param path - document path
	 * @param artikli - Document content
	 */
	public DefaultSingleDocumentModelSlika(Path path, List<Artikl> artikli, JNotepadPP notepad) {
		this.path = path;
		slika = new  Slika(artikli);
//		slika.getDocument().addDocumentListener(new DocumentListenerImpl());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Slika getTextComponent() {
		return slika;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getFilePath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFilePath(Path path) {
		if(path != null && !path.equals(this.path)) {
			this.path = path;
			notifyListenersPath();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(boolean modified) {
		if(!isModified() && modified == true) {
			this.modified = true;
			notifyListenersModify();
		}else if(isModified() && modified == false) {
			this.modified = false;
			notifyListenersModify();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if(l == null || listeners.contains(l)) return;
		
		listeners.add(l);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies listeners that document has been modified
	 */
	private void notifyListenersModify() {
		for(SingleDocumentListener listener:listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}
	
	/**
	 * Notifies listeners that document's path has changed
	 */
	private void notifyListenersPath() {
		for(SingleDocumentListener listener:listeners) {
			listener.documentFilePathUpdated(this);;
		}
	}
	
	/**
	 * Document listener which is used to get information 
	 * that document has been edited
	 * @author Josip Trbuscic
	 *
	 */
	private class DocumentListenerImpl implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
			documentChanged(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			documentChanged(e);
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			documentChanged(e);
			
		}
		
		private void documentChanged(DocumentEvent e) {
			DefaultSingleDocumentModelSlika.this.setModified(true);
		}
		
	}

}

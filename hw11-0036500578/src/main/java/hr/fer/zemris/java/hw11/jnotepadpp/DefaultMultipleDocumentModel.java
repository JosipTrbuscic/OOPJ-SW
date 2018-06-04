package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class is a model which is able to hold one or more documents.
 * Class offers methods for basic document operations such as save document
 * load document create new document and close document. Ducuments are shown 
 * in tabbed pane
 * @author Josip Trbuscic
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * List of Listeners
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * List of documents
	 */
	private List<SingleDocumentModel> documents = new ArrayList<>();
	
	/**
	 * Current document
	 */
	private SingleDocumentModel current;
	
	/**
	 * Previous document
	 */
	private SingleDocumentModel previous;
	
	/**
	 * Localization provider
	 */
	private ILocalizationProvider lp;

	/**
	 * Constructor
	 * @param lp - localization provider
	 */
	public DefaultMultipleDocumentModel(ILocalizationProvider lp) {
		this.lp = lp;
		addChangeListener(e->{
			previous = current;
			current = getSelectedIndex()==-1 ? createNewDocument():documents.get(getSelectedIndex());
			notifyDocChanged(previous, current);
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		
		documents.add(doc);
		previous = current;
		current = doc;
		
		addDocumentListener(doc);
		
		addTab(lp.getString("new"), doc.getTextComponent());
		setSelectedIndex(getTabCount() - 1);
		setIconAt(getTabCount()-1, Icons.GREEN_CIRCLE);
		
		notifyDocAdded(current);

		return doc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		int i = getIndexOf(path);
		previous = current;
		
		if (i > -1) {
			setSelectedIndex(i);
			return documents.get(i);
		}
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getParent(), lp.getString("load_error"), lp.getString("error"), JOptionPane.ERROR_MESSAGE);
			return null;
		}

		String text = new String(bytes, StandardCharsets.UTF_8);
		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, text);
		documents.add(doc);
		
		addDocumentListener(doc);

		addTab(path.getFileName().toString(), doc.getTextComponent());
		setToolTipTextAt(getTabCount() - 1, path.toAbsolutePath().toString());
		setSelectedIndex(getTabCount() - 1);
		setIconAt(getTabCount()-1, Icons.GREEN_CIRCLE);
		
		notifyDocAdded(doc);
		return doc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath != null && getIndexOf(newPath) > -1) {
			JOptionPane.showMessageDialog(getParent(), lp.getString("spec_opened"), lp.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Path dest = newPath == null ? model.getFilePath() : newPath;
			Writer writer = Files.newBufferedWriter(dest, StandardCharsets.UTF_8);
			writer.write(model.getTextComponent().getText());
			writer.close();
			model.setFilePath(dest);
			current = model;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getParent(), "File writing error", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		current.setModified(false);
		notifyDocChanged(previous, current);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = getSelectedIndex();

		for(int i = 0; i<documents.size();++i) {
			if(documents.get(i) == model) {
				index = i;
				break;
			}
		}
		SingleDocumentModel doc = documents.get(index);
		documents.remove(index);
		removeTabAt(index);
		notifyDocRemoved(doc);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if (l == null || listeners.contains(l))
			return;

		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	/**
	 * Returns index of document with given path, 
	 * or -1 if such document doesn't exist;
	 * @param path - document path
	 * @return index of document with given path
	 */
	private int getIndexOf(Path path) {
		int i = -1;
		for (SingleDocumentModel doc : documents) {
			i++;
			Path other = doc.getFilePath();
			if (other == null)
				continue;
			if (other.equals(path)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Notifies all listeners that document was added
	 * @param doc - added document
	 */
	private void notifyDocAdded(SingleDocumentModel doc) {
		for (MultipleDocumentListener listener : listeners) {
			listener.documentAdded(doc);
		}
	}

	/**
	 * Notifies all listeners that document was removed
	 * @param doc - removed document
	 */
	private void notifyDocRemoved(SingleDocumentModel doc) {
		for (MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(doc);
		}
	}

	/**
	 * Notifies all listeners that current document has changed
	 * @param previous - previous document
	 * @param current - current document
	 */
	private void notifyDocChanged(SingleDocumentModel previous, SingleDocumentModel current) {
		for (MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(previous, current);
		}
	}
	
	/**
	 * Adds {@link SingleDocumentListener} to the given
	 * {@link SingleDocumentModel}
	 * @param doc - document
	 */
	private void addDocumentListener(SingleDocumentModel doc) {
		doc.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if(model.isModified()) {
					setIconAt(getSelectedIndex(), Icons.RED_CIRCLE);
				}else {
					setIconAt(getSelectedIndex(), Icons.GREEN_CIRCLE);
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toAbsolutePath().toString());
			}
		});
	}
}

package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
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
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


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
	
	private String[] names = {"Artikl", "Postotak", "boja"};

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
	
	private JNotepadPP notepad;

	/**
	 * Constructor
	 * @param lp - localization provider
	 */
	public DefaultMultipleDocumentModel(JNotepadPP notepad) {
		this.notepad = notepad;
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
		
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		List<Artikl> artikli = null;
		try {
			artikli = parseLines(lines);
		}catch(Exception e2) {
			//TODO: novi prozor
		}
		
		SingleDocumentModel doc = new DefaultSingleDocumentModelSlika(path, artikli, notepad);
		documents.add(doc);
		Object[][] art = new Object[artikli.size()][3];
		for(int j = 0; j <artikli.size(); j++) {
		
			art[j][0] = artikli.get(j).getName();
			art[j][1] = artikli.get(j).getSize();
			art[j][2] = artikli.get(j).getCol();
		}
		
		
		JTable tablica = new JTable(art, names);
		notepad.setTablica(tablica);
		addDocumentListener(doc);

		addTab(path.getFileName().toString(), doc.getTextComponent());
		setToolTipTextAt(getTabCount() - 1, path.toAbsolutePath().toString());
		setSelectedIndex(getTabCount() - 1);
		setIconAt(getTabCount()-1, Icons.GREEN_CIRCLE);
		
		
		notifyDocAdded(doc);
		return doc;
	}
	
	private List<Artikl> parseLines(List<String> lines) {
		if(lines == null) {
			throw new IllegalArgumentException("Krivi format");
		}
		List<Artikl> artikli = new ArrayList<>();
		lines.forEach(l->{
			String[] parts = l.split("\\s+");
			if(parts.length != 2) {
				throw new IllegalArgumentException("Krivi format");
			}
			artikli.add(new Artikl(parts[0], Integer.parseInt(parts[1])));
		});
		
		return artikli;
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

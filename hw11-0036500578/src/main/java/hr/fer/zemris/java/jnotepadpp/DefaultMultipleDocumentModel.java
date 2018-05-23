package hr.fer.zemris.java.jnotepadpp;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.jnotepadpp.actions.EditActions;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	private List<SingleDocumentModel> documents = new ArrayList<>();
	private SingleDocumentModel current;

	public DefaultMultipleDocumentModel() {
		createNewDocument();
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		documents.add(doc);
		SingleDocumentModel previous = current;
		current = doc;
		doc.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(getSelectedIndex(), Icons.RED_CIRCLE);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toAbsolutePath().toString());
			}
		});
		addTab("new", doc.getTextComponent());
		setSelectedIndex(getTabCount() - 1);
		setIconAt(getTabCount()-1, Icons.GREEN_CIRCLE);
		notifyDocAdded(doc);
		notifyDocChanged(previous, current);
		return doc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		int i = getIndexOf(path);
		SingleDocumentModel previous = current;
		if (i > -1) {
			setSelectedIndex(i);
			current = documents.get(i);
			return documents.get(i);
		}
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getParent(), "Loading error", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		String text = new String(bytes, StandardCharsets.UTF_8);
		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, text);
		documents.add(doc);
		
		current = doc;
		doc.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(getSelectedIndex(), Icons.RED_CIRCLE);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toAbsolutePath().toString());
			}
		});
		addTab(path.getFileName().toString(), doc.getTextComponent());
		setToolTipTextAt(getTabCount() - 1, path.toAbsolutePath().toString());
		setSelectedIndex(getTabCount() - 1);
		setIconAt(getTabCount()-1, Icons.GREEN_CIRCLE);
		notifyDocAdded(doc);
		notifyDocChanged(previous, current);
		return doc;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath != null && getIndexOf(newPath) > -1) {
			JOptionPane.showMessageDialog(getParent(), "Specified file already opened", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		SingleDocumentModel previous = current;
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
		notifyDocChanged(previous, current);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		SingleDocumentModel previous = current;
		int index = getSelectedIndex();
		documents.remove(index);
		removeTabAt(index);
		if (getNumberOfDocuments() == 0) {
			createNewDocument();
		}
		current = documents.get(index);
		notifyDocChanged(previous, current);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if (l == null || listeners.contains(l))
			return;

		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

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

	private void notifyDocAdded(SingleDocumentModel doc) {
		for (MultipleDocumentListener listener : listeners) {
			listener.documentAdded(doc);
		}
	}

	private void notifyDocRemoved(SingleDocumentModel doc) {
		for (MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(doc);
		}
	}

	private void notifyDocChanged(SingleDocumentModel previous, SingleDocumentModel current) {
		for (MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(previous, current);
		}
	}
}

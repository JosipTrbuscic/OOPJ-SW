package hr.fer.zemris.java.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class DefaultSingleDocumentModel implements SingleDocumentModel{
	private Path path;
	private boolean modified = false;
	private JTextArea textComponent;
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	public DefaultSingleDocumentModel(Path path, String content) {
		this.path = path;
		textComponent = new  JTextArea(content);
		textComponent.getDocument().addDocumentListener(new DocumentListenerImpl());
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		if(path != null && !path.equals(this.path)) {
			this.path = path;
			notifyListenersPath();
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if(!isModified() && modified == true) {
			this.modified = true;
			notifyListenersModify();
		}else if(isModified() && modified == false) {
			this.modified = false;
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if(l == null || listeners.contains(l)) return;
		
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	private void notifyListenersModify() {
		for(SingleDocumentListener listener:listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}
	
	private void notifyListenersPath() {
		for(SingleDocumentListener listener:listeners) {
			listener.documentFilePathUpdated(this);;
		}
	}
	
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
			DefaultSingleDocumentModel.this.setModified(true);
		}
		
	}

}

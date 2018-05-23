package hr.fer.zemris.java.jnotepadpp;

public interface SingleDocumentListener {
	void documentModifyStatusUpdated(SingleDocumentModel model);

	void documentFilePathUpdated(SingleDocumentModel model);
}

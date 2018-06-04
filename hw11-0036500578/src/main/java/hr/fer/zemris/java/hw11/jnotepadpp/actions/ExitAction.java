package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Class representing action used to exit application
 * @author Josip Trbuscic
 *
 */
public class ExitAction extends LocalizableAction{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Documetns model
	 */
	private MultipleDocumentModel model;
	
	/*
	 * Localization provider
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Notepad
	 */
	private JNotepadPP notepad;
	
	/**
	 * Constructor
	 * @param lp - Localization provider
	 * @param notepad - notepad
	 * @param model - documents model
	 */
	public ExitAction(ILocalizationProvider lp, JNotepadPP notepad, MultipleDocumentModel model) {
		super("exit","exit_sh", lp);
		this.model = model;
		this.lp = lp;
		this.notepad = notepad;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = model.getNumberOfDocuments() - 1; i >= 0; --i) {
			SingleDocumentModel m = model.getDocument(i);
			if (m.isModified()) {
				Path path = m.getFilePath();
				int result = JOptionPane.showConfirmDialog(notepad,
						lp.getString("unsave_changes"), lp.getString("exit_file"),
						JOptionPane.YES_NO_CANCEL_OPTION);
				switch (result) {
				case JOptionPane.YES_OPTION:
					if (path == null) {
						saveAs(m);
					} else {
						model.saveDocument(m, path);
					}
					model.closeDocument(m);
					continue;
				case JOptionPane.NO_OPTION:
					model.closeDocument(m);
					continue;
				default:
					return;
				}

			}
			model.closeDocument(m);
		}
		notepad.dispose();
		System.exit(0);
		
	}
	
	/**
	 * Method called if edited document should be saved before exiting application
	 * @param m - document to save
	 */
	private void saveAs(SingleDocumentModel m) {
		notepad.saveAsFileChooser.setDialogTitle(lp.getString("save_as"));
		if (notepad.saveAsFileChooser.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		model.saveDocument(m, notepad.saveAsFileChooser.getSelectedFile().toPath());
	}

}

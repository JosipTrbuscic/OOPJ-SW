package hr.fer.zemris.java.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import hr.fer.zemris.java.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.jnotepadpp.SingleDocumentModel;

public class FileActions {
	private JNotepadPP notepad;
	private MultipleDocumentModel model;

	public FileActions(JNotepadPP notepad, MultipleDocumentModel model) {
		this.notepad = notepad;
		this.model = model;
	}

	public Action openFile = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			putValue(Action.NAME, "Open");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
			putValue(Action.SHORT_DESCRIPTION, "Used to open existing field from disk");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(notepad, "Datoteka" + fileName.getAbsolutePath() + "ne postoji!",
						"Pogreška", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JTextArea editor = model.loadDocument(filePath).getTextComponent();
			editor.addCaretListener(l->{
				int len = editor.getCaret().getDot()-editor.getCaret().getMark();
				EditActions.copy.setEnabled(len!=0);
				EditActions.cut.setEnabled(len!=0);
			});
		}
	};

	public Action newFile = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			putValue(Action.NAME, "New");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
			putValue(Action.SHORT_DESCRIPTION, "Used to create new empty file");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = model.createNewDocument().getTextComponent();
			editor.addCaretListener(l->{
				int len = editor.getCaret().getDot()-editor.getCaret().getMark();
				EditActions.copy.setEnabled(len!=0);
				EditActions.cut.setEnabled(len!=0);
			});
		}
	};

	public Action saveFile = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			putValue(Action.NAME, "Save");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
			putValue(Action.SHORT_DESCRIPTION, "Used to save file");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Path path = model.getCurrentDocument().getFilePath();

			if (path == null) {
				saveAs();
			} else {
				model.saveDocument(model.getCurrentDocument(), null);
			}
		}
	};

	public Action saveFileAs = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			putValue(Action.NAME, "Save As");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
			putValue(Action.SHORT_DESCRIPTION, "Used to save file as new file");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			saveAs();
		}
	};
	
	public Action closeTab = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			putValue(Action.NAME, "Close");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
			putValue(Action.SHORT_DESCRIPTION, "Used to close current tab");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.getCurrentDocument().isModified()) {
				Path path = model.getCurrentDocument().getFilePath();
				int result = JOptionPane.showConfirmDialog(notepad, "The file has unsaved changes, save now?", "Existing file",
						JOptionPane.YES_NO_CANCEL_OPTION);
				switch (result) {
				case JOptionPane.YES_OPTION:
					if(path == null) {
						saveAs();
					} else {
						model.saveDocument(model.getCurrentDocument(), path);
					}
					model.closeDocument(model.getCurrentDocument());
					return;
				case JOptionPane.NO_OPTION:
					model.closeDocument(model.getCurrentDocument());
					return;
				default:
					return;
				}
			}
		}
	};
	
	public Action statistics = new AbstractAction() {
		{
			putValue(Action.NAME, "Statistic");
			putValue(Action.SHORT_DESCRIPTION, "Used to get statistic about current file");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = model.getCurrentDocument();
			char[] text = current.getTextComponent().getText().toCharArray();
			
			int all = text.length;
			int nonWhite = 0;
			int lines = 0;
			for(char c : text ) {
				if(!Character.isWhitespace(c)) {
					nonWhite++;
				}
				if(c == '\n') {
					lines++;
				}
			}
			lines = (lines == 0 && !(text.length == 0)) ? 1 : lines;
			JOptionPane.showMessageDialog(notepad, "Your document has: "+all+" character(s),\n "+nonWhite+" non-blank character(s) and "+lines+" line(s).");
		}
	};

	private void saveAs() {
		saveAsFileChooser.setDialogTitle("Save As");
		if (saveAsFileChooser.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		model.saveDocument(model.getCurrentDocument(), saveAsFileChooser.getSelectedFile().toPath());
	}

	private final JFileChooser saveAsFileChooser = new JFileChooser() {
		private static final long serialVersionUID = 1L;

		@Override
		public void approveSelection() {
			File f = getSelectedFile();
			if (f.exists() && getDialogType() == SAVE_DIALOG) {
				int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file",
						JOptionPane.YES_NO_CANCEL_OPTION);
				switch (result) {
				case JOptionPane.YES_OPTION:
					super.approveSelection();
					return;
				case JOptionPane.CANCEL_OPTION:
					cancelSelection();
					return;
				default:
					return;
				}
			}
			super.approveSelection();
		}
	};
	
}

package hr.fer.zemris.java.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.ViewFactory;

import hr.fer.zemris.java.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.jnotepadpp.MultipleDocumentModel;

public class EditActions {

	public static Action copy = new DefaultEditorKit.CopyAction() {
		{
			putValue(Action.NAME, "Copy");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
			putValue(Action.SHORT_DESCRIPTION, "Used to copy selected text to clipboard");
		}
	};
	
	public static Action cut = new DefaultEditorKit.CutAction() {
		{
			putValue(Action.NAME, "Cut");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
			putValue(Action.SHORT_DESCRIPTION, "Used to cut selected text to clipboard");
		}
	};
	
	public static Action paste = new DefaultEditorKit.PasteAction() {
		{
			putValue(Action.NAME, "Paste");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
			putValue(Action.SHORT_DESCRIPTION, "Used to paste text from clipboard to current caret position");
		}
	};

}

package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Class containing actions which are used to edit current document(copy, cut and paste).
 * @author Josip Trbuscic
 *
 */
public class EditActions {
	
	/**
	 * Documents model
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Localization provider
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Copy selection action
	 */
	public Action copy;
	
	/**
	 * Cut selection action
	 */
	public Action cut;
	
	/**
	 * Paste clipboard content action
	 */
	public Action paste;
	
	/**
	 * Constructor
	 * @param model
	 * @param lp
	 */
	public EditActions(MultipleDocumentModel model, ILocalizationProvider lp) {
		this.model = model;
		this.lp = lp;
		initActions();
	}
	
	/**
	 * Initializes actions
	 */
	private void initActions() {
		copy = new LocalizableAction("copy","copy_sh", lp) {
			private static final long serialVersionUID = 1L;
			{
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
				setEnabled(false);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				editor.copy();
			}
		};
		
		paste = new LocalizableAction("paste","paste_sh", lp) {
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				editor.paste();
			}
		};
		
		cut = new LocalizableAction("cut","cut_sh", lp) {
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
				setEnabled(false);
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				editor.cut();
			}
		};
	}
}

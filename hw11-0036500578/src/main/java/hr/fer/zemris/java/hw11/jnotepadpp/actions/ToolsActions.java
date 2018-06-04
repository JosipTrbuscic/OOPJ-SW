package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Class containing actions used change case of selected string
 * @author Josip Trbuscic
 *
 */
public class ToolsActions {
	/**
	 * Documents model
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Localization provider
	 */
	private ILocalizationProvider lp;
	
	/**
	 * String to lower case action
	 */
	public Action lowerCase;
	
	/**
	 * String to upper case action
	 */
	public Action upperCase;
	
	/**
	 * Toggle case action
	 */
	public Action invertCase;

	/**
	 * Constructor 
	 * @param model - documents model
	 * @param lp - localization provider
	 */
	public ToolsActions(MultipleDocumentModel model, ILocalizationProvider lp) {
		this.model = model;
		this.lp = lp;
		
		initActions();
	}

	/**
	 * Initializes actions
	 */
	private void initActions() {
		lowerCase = new LocalizableAction("to_lower_case","to_lower_case_sh",  lp) {
			private static final long serialVersionUID = 1L;
			{
				setEnabled(false);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				String selected = model.getCurrentDocument().getTextComponent().getSelectedText().toLowerCase();
				editor.replaceSelection(selected);
			}
			
		};
		
		upperCase = new LocalizableAction("to_upper_case","to_upper_case_sh", lp) {
			private static final long serialVersionUID = 1L;
			{
				setEnabled(false);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				String selected = model.getCurrentDocument().getTextComponent().getSelectedText().toUpperCase();
				editor.replaceSelection(selected);
			}
			
		};
		
		invertCase = new LocalizableAction("invert_case","invert_case_sh", lp) {
			private static final long serialVersionUID = 1L;
			{
				setEnabled(false);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				String selected = model.getCurrentDocument().getTextComponent().getSelectedText();
				editor.replaceSelection(toggleCase(selected));
			}
			
		};
		
	}
	
	/**
	 * Method used to toggle case of given string
	 * @param text - string whose case will be toggled
	 * @return string with toggled case
	 */
	private static String toggleCase(String text){
	    char[] chars = text.toCharArray();
	    for (int i = 0; i < chars.length; i++){
	        char c = chars[i];
	        
	        if (Character.isUpperCase(c)) {
	            chars[i] = Character.toLowerCase(c);
	        } else if (Character.isLowerCase(c)){
	            chars[i] = Character.toUpperCase(c);
	        }
	    }
	    return new String(chars);
	}
	
}

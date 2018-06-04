package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Class containing actions used to sort and filter selected 
 * lines of document
 * @author Josip Trbuscic
 *
 */
public class SortAndUniqueActions {
	/**
	 * Documents model
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Localization provider
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Sort lines in ascending order action
	 */
	public Action ascending;
	
	/**
	 * Sort lines in descending order action
	 */
	public Action descending;
	
	/**
	 * Remove duplicate lines action
	 */
	public Action unique;
	
	/**
	 * Offset of starting line
	 */
	int start;
	
	/**
	 * Offset of ending line
	 */
	int end;
	
	/**
	 * Constructor
	 * @param model - documents model
	 * @param lp - localization provider
	 */
	public SortAndUniqueActions(MultipleDocumentModel model, ILocalizationProvider lp) {
		this.model = model;
		this.lp = lp;
		start = 0;
		end = 0;
		
		initActions();
	}

	/**
	 * Initializes actions
	 */
	private void initActions() {
		ascending = new LocalizableAction("asc","asc_sh", lp) {
			private static final long serialVersionUID = 1L;

			{
				setEnabled(false);
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Locale locale = new Locale(lp.getString("locale"));
				Collator collator = Collator.getInstance(locale);
				
				List<String> list = getLines();
				
				Collections.sort(list, collator);
				write(list);
			}
			
		};
		
		descending = new LocalizableAction("desc","desc_sh", lp) {
			private static final long serialVersionUID = 1L;

			{
				setEnabled(false);
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Locale locale = new Locale(lp.getString("locale"));
				Collator collator = Collator.getInstance(locale);
				
				List<String> list = getLines();
				
				Collections.sort(list, collator.reversed() );
				write(list);
			}
			
		};
		
		unique = new LocalizableAction("uniq","uniq_sh", lp) {
			private static final long serialVersionUID = 1L;

			{
				setEnabled(false);
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> lines = getLines();
				lines = lines.stream().distinct().collect(Collectors.toList());
				
				write(lines);
			}
			
		};
		
	}
	
	/**
	 * Returns selected lines as list
	 * @return selected lines as list
	 */
	private List<String> getLines(){
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Caret caret = editor.getCaret();
		
		int caretStart = caret.getDot()>caret.getMark() ? caret.getMark() : caret.getDot();
		int caretEnd = caret.getDot()<caret.getMark() ? caret.getMark() : caret.getDot();
		
		String text = null;
		try {
			int startLine = editor.getLineOfOffset(caretStart);
			int endLine = editor.getLineOfOffset(caretEnd);
			
			start = editor.getLineStartOffset(startLine);
			end = editor.getLineEndOffset(endLine);
			
			text = editor.getDocument().getText(start, end-start);
		} catch (BadLocationException ignorable) {}
		
		String[] parts = text.split("\n");
		List<String> list = Arrays.asList(parts);
		
		return list;
	}
	
	/**
	 * Writes given list of string to a document
	 * @param list - list of string to be written
	 */
	private void write(List<String> list) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();

		StringBuilder sb = new StringBuilder();
		list.forEach(s->sb.append(s).append("\n"));
		try {
			editor.getDocument().remove(start, end-start);
			editor.getDocument().insertString(start, sb.toString(),null);
		} catch (BadLocationException e) {}
	}
}

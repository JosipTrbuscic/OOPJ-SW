package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;

/**
 * This class represents status bar which displays informations 
 * about current document and time.
 * @author Josip Trbuscic
 *
 */
public class JNotepadppStatusBar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Label used to display document length
	 */
	private JLabel lengthLabel;
	
	/**
	 * Label used to display caret info
	 */
	private JLabel caretInfoLabel;
	
	/**
	 * Label used to display current time
	 */
	private JLabel timeInfoLabel;
	
	/**
	 * Time formatter
	 */
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
	
	/**
	 * Localization Provider
	 */
	private ILocalizationProvider lp;

	/**
	 * Constructor
	 * @param lp - localization provider
	 */
	public JNotepadppStatusBar(ILocalizationProvider lp) {
		this.lp = lp;
		initStatusBar();
		updateTime();
		startClock();
	}

	/**
	 * Initializes all component of the status bar
	 */
	private void initStatusBar() {
		setLayout(new GridLayout(0, 3));
		setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.DARK_GRAY));

		lengthLabel = new LJLabel(lp.getString("length"), lp);
		lengthLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.DARK_GRAY));

		caretInfoLabel = new JLabel(" Ln:0  Col:0");
		caretInfoLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.DARK_GRAY));

		timeInfoLabel = new JLabel();
		timeInfoLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.DARK_GRAY));
		timeInfoLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		add(lengthLabel);
		add(caretInfoLabel);
		add(timeInfoLabel);
	}
	
	/**
	 * Used to update info about current document
	 * @param editor - current document editor
	 */
	public void updateStatus(JTextArea editor) {
		Caret caret = editor.getCaret();
		int pos = editor.getCaretPosition();
		int sel = caret.getDot() - caret.getMark();
		int ln = 0;
		int col = 0;
		try {
			ln = editor.getLineOfOffset(pos);
			col = pos - editor.getLineStartOffset(ln);
		} catch (BadLocationException e1) {
			// ignorable
		}
		lengthLabel.setText(lp.getString("length")+":" + editor.getText().length());
		caretInfoLabel.setText(" Ln:" + (ln + 1) + "  Col:" + col + (sel != 0 ? "  Sel:" + Math.abs(sel) : ""));
	}

	/**
	 * Updates time currently displayed
	 */
	private void updateTime() {
		timeInfoLabel.setText(formatter.format(LocalDateTime.now()));
	}

	/**
	 * Starts clock which will periodically call 
	 * method to update displayed time
	 */
	private void startClock() {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (Exception ex) {
				}
				SwingUtilities.invokeLater(() -> {
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}
}

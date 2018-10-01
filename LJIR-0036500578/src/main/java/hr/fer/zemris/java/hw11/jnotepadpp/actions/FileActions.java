package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.Artikl;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

/**
 * Class which contains actions used to open, save and close document.
 * It also provides action to create new empty document as well as 
 * displaying statistics about document.
 * @author Josip Trbuscic
 *
 */
public class FileActions {
	/**
	 * Notepad
	 */
	private JNotepadPP notepad;
	
	/**
	 * Documents model
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Localization provider
	 */
	
	/**
	 * Open file action
	 */
	public Action openFile;
	
	/**
	 * Create new empty file action
	 */
	public Action newFile;
	
	/**
	 * Save file action
	 */
	public Action saveFile;
	
	/**
	 * Save as new file action
	 */
	public Action saveFileAs;
	
	/**
	 * Display statistic action
	 */
	public Action statistics;
	
	/**
	 * Close document tab action
	 */
	public Action closeTab;

	/**
	 * Constructor
	 * @param notepad - notepad
	 * @param model - documents model
	 * @param lp - localization provider
	 */
	public FileActions(JNotepadPP notepad, MultipleDocumentModel model) {
		this.notepad = notepad;
		this.model = model;
		
		initActions();
	}
	
	/**
	 * Initializes actions
	 */
	private void initActions() {
		openFile = (Action) new AbstractAction(){
			private static final long serialVersionUID = 1L;

			{
				putValue(NAME, "Open");
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
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
					JOptionPane.showMessageDialog(notepad, "no file",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
//				List<String> lines = null;
//				try {
//					lines = Files.readAllLines(filePath);
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//				
//				List<Artikl> artikli = null;
//				try {
//					artikli = parseLines(lines);
//				}catch(Exception e2) {
//					//TODO: novi prozor
//				}
				
				model.loadDocument(filePath);
			}


//			private List<Artikl> parseLines(List<String> lines) {
//				if(lines == null) {
//					throw new IllegalArgumentException("Krivi format");
//				}
//				List<Artikl> artikli = new ArrayList<>();
//				lines.forEach(l->{
//					String[] parts = l.split("\\s+");
//					if(parts.length != 2) {
//						throw new IllegalArgumentException("Krivi format");
//					}
//					artikli.add(new Artikl(parts[0], Integer.parseInt(parts[1])));
//				});
//				
//				return artikli;
//			}
		};

	}
		

	/**
	 * Method called if edited document should be saved as new file.
	 * @param m - document to save
	 */

	/**
	 * Modified {@link JFileChooser} which offers option to overwrite
	 * selected file or cancel saving
	 */
	
}

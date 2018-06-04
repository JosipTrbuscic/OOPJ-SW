package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

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
	private ILocalizationProvider lp;
	
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
	public FileActions(JNotepadPP notepad, MultipleDocumentModel model, ILocalizationProvider lp) {
		this.notepad = notepad;
		this.model = model;
		this.lp = lp;
		
		initActions();
	}
	
	/**
	 * Initializes actions
	 */
	private void initActions() {
		openFile = new LocalizableAction("open","open_sh", lp){
			private static final long serialVersionUID = 1L;

			{
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
					JOptionPane.showMessageDialog(notepad, lp.getString("file") + fileName.getAbsolutePath() + lp.getString("not_exist"),
							lp.getString("error"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				model.loadDocument(filePath);
			}
		};

		newFile = new LocalizableAction("new","new_sh", lp){
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};
		
		saveFile =  new LocalizableAction("save","save_sh", lp) {
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
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

		saveFileAs = new LocalizableAction("save_as","save_as_sh",lp) {
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		};
		
		closeTab = new LocalizableAction("close","close_sh",lp) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getCurrentDocument().isModified()) {
					Path path = model.getCurrentDocument().getFilePath();
					int result = JOptionPane.showConfirmDialog(notepad, lp.getString("unsave_changes"),  lp.getString("exit_file"),
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
				model.closeDocument(model.getCurrentDocument());
			}
		};
		
		statistics = new LocalizableAction("stat","stat_sh",lp){
			private static final long serialVersionUID = 1L;
			
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
				JOptionPane.showMessageDialog(notepad
						, lp.getString("your_doc")+" "+all+ " "+lp.getString("chars")+" "+nonWhite+" "+ lp.getString("non_blank")+" "+lines+" "+ lp.getString("line")
						, lp.getString("stat")
						, JOptionPane.INFORMATION_MESSAGE)
				;
			}
		};
	}

	/**
	 * Method called if edited document should be saved as new file.
	 * @param m - document to save
	 */
	private void saveAs() {
		saveAsFileChooser.setDialogTitle("Save As");
		if (saveAsFileChooser.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		model.saveDocument(model.getCurrentDocument(), saveAsFileChooser.getSelectedFile().toPath());
	}

	/**
	 * Modified {@link JFileChooser} which offers option to overwrite
	 * selected file or cancel saving
	 */
	private final JFileChooser saveAsFileChooser = new JFileChooser() {
		private static final long serialVersionUID = 1L;

		@Override
		public void approveSelection() {
			File f = getSelectedFile();
			if (f.exists() && getDialogType() == SAVE_DIALOG) {
				int result = JOptionPane.showConfirmDialog(notepad, lp.getString("file_exists"), lp.getString("exit_file"),
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

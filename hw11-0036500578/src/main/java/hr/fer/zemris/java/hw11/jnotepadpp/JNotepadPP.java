package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.EditActions;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.FileActions;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortAndUniqueActions;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToolsActions;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This class represents simple text editor. This editor offers users basic 
 * text editing functions such as cut, copy and paste as well as some advanced one
 * such as sorting lines and removing duplicate lines. There can be multiple documents 
 * opened at once and editor offers user to get statistic about current one 
 * @author Josip Trbuscic
 *
 */
public class JNotepadPP extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Localization provider
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Documents model
	 */
	private MultipleDocumentModel tabPane;
	
	/**
	 * File navigation actions
	 */
	private FileActions fileActions;
	
	/**
	 * Document editing actions
	 */
	private EditActions editActions;
	
	/**
	 * Caret listener
	 */
	private CaretListener caretListener;
	
	/**
	 * Status bar
	 */
	private JNotepadppStatusBar statusBar;
	
	/**
	 * String editing actions
	 */
	private ToolsActions toolsActions;
	
	/**
	 * Line sorting and filtering actions 
	 */
	private SortAndUniqueActions sortAndUniqueActions;

	/**
	 * Constructor.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListenerImpl());
		setLocation(100, 50);
		setSize(800, 800);
		setTitle("JNotepad++");

		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		tabPane = new DefaultMultipleDocumentModel(flp);

		fileActions = new FileActions(this, tabPane, flp);
		editActions = new EditActions(tabPane, flp);
		toolsActions = new ToolsActions(tabPane, flp);
		sortAndUniqueActions = new SortAndUniqueActions(tabPane, flp);

		caretListener = getCaretListener();
		setDocumentListener();

		initGUI();
	}

	/**
	 * Initializes gui
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		createMenuBar();
		JPanel centerPanel = new JPanel(new BorderLayout());
		statusBar = new JNotepadppStatusBar(flp);
		centerPanel.add(new JScrollPane((JTabbedPane) tabPane), BorderLayout.CENTER);
		centerPanel.add(statusBar, BorderLayout.SOUTH);
		createToolBar();

		cp.add(centerPanel, BorderLayout.CENTER);
		tabPane.createNewDocument();
	}

	/**
	 * Creates menu bar
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(new LocalizableAction("file","none", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {}
		});
		menuBar.add(fileMenu);
		fileMenu.add(fileActions.newFile);
		fileMenu.addSeparator();
		fileMenu.add(fileActions.openFile);
		fileMenu.addSeparator();
		fileMenu.add(fileActions.saveFileAs);
		fileMenu.add(fileActions.saveFile);
		fileMenu.addSeparator();
		fileMenu.add(fileActions.closeTab);
		JMenu editMenu = new JMenu(new LocalizableAction("edit","none", flp) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuBar.add(editMenu);
		editMenu.add(editActions.copy);
		editMenu.add(editActions.cut);
		editMenu.add(editActions.paste);

		initLanguages(menuBar);
		initTools(menuBar);
		initSort(menuBar);
		menuBar.add(new JButton(sortAndUniqueActions.unique));
		menuBar.add(new JButton(fileActions.statistics));
		menuBar.add(new JButton(new ExitAction(flp, this, tabPane)));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Initializes sorting actions menu
	 * @param menuBar - menu bar
	 */
	private void initSort(JMenuBar menuBar) {
		JMenu sorts = new JMenu(new LocalizableAction("sort","none", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		sorts.add(sortAndUniqueActions.ascending);
		sorts.add(sortAndUniqueActions.descending);
		menuBar.add(sorts);
	}

	/**
	 * Initializes tools actions menu
	 * @param menuBar -  menu bar
	 */
	private void initTools(JMenuBar menuBar) {
		JMenu tools = new JMenu(new LocalizableAction("tools","none", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {}
		});

		tools.add(toolsActions.upperCase);
		tools.add(toolsActions.lowerCase);
		tools.add(toolsActions.invertCase);
		menuBar.add(tools);
	}

	/**
	 * Initializes language chooser menu
	 * @param menuBar - menubar
	 */
	private void initLanguages(JMenuBar menuBar) {
		JMenu languages = new JMenu(new LocalizableAction("lang","lang_sh", flp) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		addLanguage(languages, "hr");
		addLanguage(languages, "en");
		addLanguage(languages, "de");
		menuBar.add(languages);
	}

	/**
	 * Adds language to the given menu
	 * @param languages - language menu
	 * @param lang - language
	 */
	private void addLanguage(JMenu languages, String lang) {
		languages.add(new JMenuItem(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.NAME, lang);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage(lang);

			}
		}));
	}

	/**
	 * Initializes toolbar
	 */
	private void createToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setName(flp.getString("toolbar")+" - JNotepad++");
		toolbar.add(new JButton(fileActions.newFile));
		toolbar.add(new JButton(fileActions.openFile));
		toolbar.add(new JButton(fileActions.saveFile));
		toolbar.add(new JButton(fileActions.saveFileAs));
		toolbar.add(new JButton(fileActions.closeTab));
		toolbar.add(new JLabel("     "));
		toolbar.add(new JButton(editActions.copy));
		toolbar.add(new JButton(editActions.cut));
		toolbar.add(new JButton(editActions.paste));
		toolbar.add(new JLabel("     "));
		toolbar.add(new JButton(fileActions.statistics));
		toolbar.add(new JLabel("     "));
		toolbar.add(new JButton(new ExitAction(flp, this, tabPane)), RIGHT_ALIGNMENT);

		toolbar.setFloatable(true);
		getContentPane().add(toolbar, BorderLayout.NORTH);
	}

	/**
	 * Window listener which prompts user to save changes if there 
	 * are documents with unsaved changes
	 * @author Josip Trbuscic
	 *
	 */
	private class WindowListenerImpl extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			setVisible(true);
			for (int i = tabPane.getNumberOfDocuments() - 1; i >= 0; --i) {
				SingleDocumentModel m = tabPane.getDocument(i);
				if (m.isModified()) {
					Path path = m.getFilePath();
					int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
							flp.getString("unsave_changes"), flp.getString("exit_file"),
							JOptionPane.YES_NO_CANCEL_OPTION);
					switch (result) {
					case JOptionPane.YES_OPTION:
						if (path == null) {
							saveAs(m);
						} else {
							tabPane.saveDocument(m, path);
						}
						tabPane.closeDocument(m);
						continue;
					case JOptionPane.NO_OPTION:
						tabPane.closeDocument(m);
						continue;
					default:
						return;
					}

				}
				tabPane.closeDocument(m);
			}
			dispose();
			System.exit(0);
		}

		/* * Method called if edited document should be saved before exiting application
		 * @param m - document to save
		 */
		private void saveAs(SingleDocumentModel m) {
			saveAsFileChooser.setDialogTitle(flp.getString("save_as"));
			if (saveAsFileChooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			tabPane.saveDocument(m, saveAsFileChooser.getSelectedFile().toPath());
		}

	}


	/**
	 * Modified {@link JFileChooser} which offers option to overwrite
	 * selected file or cancel saving
	 */
	public final JFileChooser saveAsFileChooser = new JFileChooser() {
		private static final long serialVersionUID = 1L;

		@Override
		public void approveSelection() {
			File f = getSelectedFile();
			if (f.exists() && getDialogType() == SAVE_DIALOG) {
				int result = JOptionPane.showConfirmDialog(this, flp.getString("file_exist"), flp.getString("exit_file"),
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

	/**
	 * Returns caret listener
	 * @return caret listener
	 */
	private CaretListener getCaretListener() {
		CaretListener l = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea editor = tabPane.getCurrentDocument().getTextComponent();
				Caret caret = editor.getCaret();
				int len = caret.getDot() - caret.getMark();

				toolsActions.invertCase.setEnabled(len != 0);
				toolsActions.lowerCase.setEnabled(len != 0);
				toolsActions.upperCase.setEnabled(len != 0);
				editActions.cut.setEnabled(len != 0);
				editActions.copy.setEnabled(len != 0);
				sortAndUniqueActions.ascending.setEnabled(len != 0);
				sortAndUniqueActions.descending.setEnabled(len != 0);
				sortAndUniqueActions.unique.setEnabled(len != 0);
				statusBar.updateStatus(editor);
			}
		};

		return l;
	}

	/**
	 * Sets new document listener used to update GUI when 
	 * document change has happened
	 */
	private void setDocumentListener() {
		tabPane.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				previousModel.getTextComponent().removeCaretListener(caretListener);
				currentModel.getTextComponent().addCaretListener(caretListener);

				Path path = currentModel.getFilePath();
				String name = path == null ? flp.getString("untit") : path.toString();

				JNotepadPP.this.setTitle(name + " - JNotepad++");
			}
		});

	}

	/**
	 * Starting method
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}

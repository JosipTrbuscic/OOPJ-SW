package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.FileActions;

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

	JPanel centerPanel;
	/**
	 * Localization provider
	 */
	
	/**
	 * Documents model
	 */
	private MultipleDocumentModel tabPane;
	
	/**
	 * File navigation actions
	 */
	private FileActions fileActions;
	
	private JTable tablica;
	/**
	 * Document editing actions
	 */
	
	private String[] names = {"Artikl", "Postotak", "boja"};
	/**
	 * Caret listener
	 */
	private CaretListener caretListener;
	
	/**
	 * Status bar
	 */

	/**
	 * Constructor.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100, 50);
		setSize(800, 800);
		setTitle("JNotepad++");

		tabPane = new DefaultMultipleDocumentModel(this);

		fileActions = new FileActions(this, tabPane);
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		createMenuBar();
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(new JScrollPane((JTabbedPane) tabPane), BorderLayout.CENTER);
		tablica = new JTable(new Object[3][3], names);
		centerPanel.add(tablica, BorderLayout.EAST);
//		centerPanel.add(statusBar, BorderLayout.SOUTH);
//		createToolBar();

		cp.add(centerPanel, BorderLayout.CENTER);
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}
	
	public JTable getTablica() {
		return tablica;
	}

	public void setTablica(JTable tablica) {
		this.tablica = tablica;
		getCenterPanel().add(tablica, BorderLayout.EAST);
		getCenterPanel().paintComponents(getGraphics());
	}
	/**
	 * Creates menu bar
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
//		fileMenu.add(fileActions.newFile);
//		fileMenu.addSeparator();
		fileMenu.add(fileActions.openFile);
//		fileMenu.add(fileActions.saveFileAs);
//		fileMenu.add(fileActions.saveFile);
//		fileMenu.addSeparator();
//		fileMenu.add(fileActions.closeTab);
//		JMenu editMenu = new JMenu(new LocalizableAction("edit","none", flp) {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//			}
//		});
//		menuBar.add(editMenu);
//		editMenu.add(editActions.copy);
//		editMenu.add(editActions.cut);
//		editMenu.add(editActions.paste);

//		initLanguages(menuBar);
//		initTools(menuBar);
//		initSort(menuBar);
//		menuBar.add(new JButton(sortAndUniqueActions.unique));
//		menuBar.add(new JButton(fileActions.statistics));
//		menuBar.add(new JButton(new ExitAction(flp, this, tabPane)));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Initializes sorting actions menu
	 * @param menuBar - menu bar


	/**
	 * Initializes toolbar
	 */
//	private void createToolBar() {
//		JToolBar toolbar = new JToolBar();
//		toolbar.setName(flp.getString("toolbar")+" - JNotepad++");
//		toolbar.add(new JButton(fileActions.newFile));
//		toolbar.add(new JButton(fileActions.openFile));
//		toolbar.add(new JButton(fileActions.saveFile));
//		toolbar.add(new JButton(fileActions.saveFileAs));
//		toolbar.add(new JButton(fileActions.closeTab));
//		toolbar.add(new JLabel("     "));
//		toolbar.add(new JButton(editActions.copy));
//		toolbar.add(new JButton(editActions.cut));
//		toolbar.add(new JButton(editActions.paste));
//		toolbar.add(new JLabel("     "));
//		toolbar.add(new JButton(fileActions.statistics));
//		toolbar.add(new JLabel("     "));
//		toolbar.add(new JButton(new ExitAction(flp, this, tabPane)), RIGHT_ALIGNMENT);
//
//		toolbar.setFloatable(true);
//		getContentPane().add(toolbar, BorderLayout.NORTH);
//	}

	/**
	 * Window listener which prompts user to save changes if there 
	 * are documents with unsaved changes
	 * @author Josip Trbuscic
	 *
	 */


	/**
	 * Modified {@link JFileChooser} which offers option to overwrite
	 * selected file or cancel saving
	 */

	

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

				Path path = currentModel.getFilePath();
				String name = path == null ? "unit" : path.toString();

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

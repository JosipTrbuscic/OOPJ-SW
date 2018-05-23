package hr.fer.zemris.java.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.jnotepadpp.actions.EditActions;
import hr.fer.zemris.java.jnotepadpp.actions.FileActions;

public class JNotepadPP extends JFrame{

	private MultipleDocumentModel tabPane = new DefaultMultipleDocumentModel();
	private FileActions fileActions  = new FileActions(this, tabPane);
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100, 50);
		setSize(800, 800);
		setTitle("JNotepad++");
		
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		createMenuBar();
		cp.add(new JScrollPane((JTabbedPane)tabPane), BorderLayout.CENTER);
		createToolBar();
		tabPane.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				Path path = currentModel.getFilePath();
				String name = path == null ? "Untitled Document" : path.toString();
				
				JNotepadPP.this.setTitle(name + " - JNotepad++");
			}
		});
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(fileActions.newFile);
		fileMenu.addSeparator();
		fileMenu.add(fileActions.openFile);
		fileMenu.addSeparator();
		fileMenu.add(fileActions.saveFileAs);
		fileMenu.add(fileActions.saveFile);
		fileMenu.addSeparator();
		fileMenu.add(fileActions.closeTab);
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		editMenu.add(EditActions.copy);
		editMenu.add(EditActions.cut);
		editMenu.add(EditActions.paste);
		menuBar.add(new JButton(fileActions.statistics));
		this.setJMenuBar(menuBar);
	}
	
	private void createToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setName("Toolbar - JNotepad++");
		toolbar.add(new JButton("Gumb"));
		toolbar.setFloatable(true);
		getContentPane().add(toolbar, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new JNotepadPP().setVisible(true);
		});
	}
}

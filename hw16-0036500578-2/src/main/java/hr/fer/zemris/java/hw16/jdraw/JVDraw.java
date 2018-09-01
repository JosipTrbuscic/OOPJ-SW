package hr.fer.zemris.java.hw16.jdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jdraw.actions.FileActions;
import hr.fer.zemris.java.hw16.jdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jdraw.colors.JColorLabel;
import hr.fer.zemris.java.hw16.jdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModel;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModelListener;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jdraw.tools.DrawCircleTool;
import hr.fer.zemris.java.hw16.jdraw.tools.DrawFilledCircleTool;
import hr.fer.zemris.java.hw16.jdraw.tools.DrawFilledPolygonTool;
import hr.fer.zemris.java.hw16.jdraw.tools.DrawLineTool;
import hr.fer.zemris.java.hw16.jdraw.tools.Tool;

/**
 * Implementation of simple paint program which allows user 
 * to draw lines and circles over the surface. Every drawn picture
 * can be exported in gif, jpg or png format or saved as formated text file.
 * @author Josip Trbuscic
 *
 */
public class JVDraw extends JFrame{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default foreground color
	 */
	private static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
	
	/**
	 * default background color
	 */
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
	
	/**
	 * background color provider
	 */
	private IColorProvider bgProvider;
	
	/**
	 * foreground color provider
	 */
	private IColorProvider fgProvider;

	/**
	 * Open, save, save as and export action
	 */
	private FileActions fileActions;
	
	/**
	 * Group of state buttons
	 */
	private ButtonGroup buttonGroup;
	
	/**
	 * Available states
	 */
	private Map<String, Tool> states;
	
	/**
	 * Current state
	 */
	private static Tool currentState;
	
	/**
	 * Drawing model
	 */
	private DrawingModel model;
	
	/**
	 * Canvas
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * Path of current file
	 */
	private Path filePath = null;
	
	/**
	 * Change listener
	 */
	private ModelChangeListener changeListener;
	
	/**
	 * Constructor
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListenerImpl());
		setLocation(100, 50);
		setSize(800, 800);
		setTitle("JVDraw");
		
		fileActions = new FileActions(this);
		bgProvider = new JColorArea(DEFAULT_BACKGROUND_COLOR);
		fgProvider = new JColorArea(DEFAULT_FOREGROUND_COLOR);
		model = new DrawingModelImpl();
		states = new HashMap<>();
		canvas = new JDrawingCanvas(model);
		createStates();
		changeListener = new ModelChangeListener();
		model.addDrawingModelListener(changeListener);
		initGUI();
	}
	
	/**
	 * Creates available drawing states
	 */
	private void createStates() {
		states.put("line", new DrawLineTool(fgProvider, model, canvas));
		states.put("circle", new DrawCircleTool(fgProvider, model, canvas));
		states.put("fcircle", new DrawFilledCircleTool(fgProvider,bgProvider , model, canvas));
		states.put("fpoly", new DrawFilledPolygonTool(fgProvider,bgProvider , model, canvas));
	}

	/**
	 * Initializes graphical interface
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		createMenuBar();
		createToolBar();
		createColorLabel();
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		cp.add(centerPanel, BorderLayout.CENTER);
		
		CanvasMouseListener listener = new CanvasMouseListener();
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
		centerPanel.add(canvas, BorderLayout.CENTER);
		model.addDrawingModelListener(canvas);
		
		JList<GeometricalObject> list = new JList<>(new DrawingObjectListModel(model));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int index = list.getSelectedIndex();
					if(index == -1) return;
					GeometricalObjectEditor editor = model.getObject(index).createGeometricalObjectEditor();
					System.out.println(model.getObject(index));
					boolean correctInput = false;
					while(!correctInput && JOptionPane.showConfirmDialog(JVDraw.this, editor,"Change" , JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION ) {
						correctInput = true;
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage());
							correctInput = false;
						}
					}
				}
			}
		});
		
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					int index = list.getSelectedIndex();
					if(index == -1) return;
					model.remove(model.getObject(index));
					list.setSelectedIndex(index);
					
				} else if(e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_ADD) {
					if(model.getSize() == 1) return;
					int index = list.getSelectedIndex();
					if(index == -1 || index == 0) return;
					model.changeOrder(model.getObject(index),-1);
					list.setSelectedIndex(index-1);
					
				} else if(e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT) {

					if(model.getSize() == 1) return;
					int index = list.getSelectedIndex();
					if(index == -1 || index == model.getSize()-1) return;
					model.changeOrder(model.getObject(index),1);
					list.setSelectedIndex(index+1);
				}
			}
		});
		centerPanel.add(new JScrollPane(list), BorderLayout.EAST);
	}
	
	/**
	 * Creates buttons used to change state
	 * @param toolbar - toolbar where buttons are added
	 */
	private void createButtons(JToolBar toolbar) {
		JRadioButton lineButton = new JRadioButton("Line");
		lineButton.addActionListener(l->{
			currentState = states.get("line");
		});
		
		JRadioButton circleButton = new JRadioButton("Circle");
		circleButton.addActionListener(l->{
			currentState = states.get("circle");
		});
		
		JRadioButton filledCircleButton = new JRadioButton("Filled Circle");
		filledCircleButton.addActionListener(l->{
			currentState = states.get("fcircle");
		});
		
		JRadioButton filledPolygonButon = new JRadioButton("Filled Polygon");
		filledPolygonButon.addActionListener(l->{
			currentState = states.get("fpoly");
		});
		
		lineButton.setSelected(true);
		currentState = states.get("line");
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(lineButton);
		buttonGroup.add(circleButton);
		buttonGroup.add(filledCircleButton);
		buttonGroup.add(filledPolygonButon);


		toolbar.add(lineButton);
		toolbar.add(circleButton);
		toolbar.add(filledCircleButton);
		toolbar.add(filledPolygonButon);
	}

	/**
	 *	Creates label which displays current colors
	 */
	private void createColorLabel() {
		ColorChangeListener colorListener = new JColorLabel(fgProvider, bgProvider);
		
		fgProvider.addColorChangeListener(colorListener);
		bgProvider.addColorChangeListener(colorListener);
		
		getContentPane().add((JColorLabel) colorListener, BorderLayout.SOUTH);
	}

	/**
	 * Creates menu bar
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(fileActions.openFile);
		fileMenu.addSeparator();
		fileMenu.add(fileActions.saveFileAs);
		fileMenu.add(fileActions.saveFile);
		fileMenu.addSeparator();
		fileMenu.add(fileActions.export);

		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Initializes toolbar
	 */
	private void createToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setName("Toolbar");
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setMaximumSize(new Dimension(5, 15));
		toolbar.add((JColorArea)fgProvider);
		toolbar.add(sep);
		toolbar.add((JColorArea)bgProvider);
		
		createButtons(toolbar);
		toolbar.setFloatable(true);
		getContentPane().add(toolbar, BorderLayout.NORTH);
	}

	/**
	 * Window listener implementation which 
	 * prompts user to save document if unsaved 
	 * changes exist
	 * @author Josip Trbuscic
	 *
	 */
	private class WindowListenerImpl extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			if(changeListener.isModelChanged()) {
				Path path = getFilePath();
				int result = JOptionPane.showConfirmDialog(JVDraw.this,
						"You have unsaved changes, do you want to save ?", "Exit",
						JOptionPane.YES_NO_CANCEL_OPTION);
				switch (result) {
				case JOptionPane.YES_OPTION:
					if (path == null) {
						fileActions.saveAs();
					} else {
						List<String> lines = getJvdFile();
						try {
							if(!path.toString().endsWith(".jvd")) {
								path = Paths.get(path + ".jvd");
							}
							Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
							
						} catch (IOException e1) {
						}
					}
					changeListener.setModelChanged(false);
					break;
				case JOptionPane.NO_OPTION:
					break;
				default:
					return;
				}
			}
			
			dispose();
			System.exit(0);
		}
			
	}
	
	/**
	 * Listener which informs current state about every mouse 
	 * movement over canvas
	 * @author Josip Trbuscic
	 *
	 */
	private class CanvasMouseListener implements MouseListener, MouseMotionListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			currentState.mouseClicked(e);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			currentState.mousePressed(e);
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			currentState.mouseReleased(e);
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			currentState.mouseDragged(e);
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			currentState.mouseMoved(e);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
		}
		
	}
	
	/**
	 * Listener which tracks model has been changed since last save
	 * @author Josip Trbuscic
	 *
	 */
	public static class ModelChangeListener implements DrawingModelListener{
		private boolean modelChanged = false;
		@Override
		public void objectsAdded(DrawingModel source, int index0, int index1) {
			modelChanged = true;
		}

		@Override
		public void objectsRemoved(DrawingModel source, int index0, int index1) {
			modelChanged = true;			
		}

		@Override
		public void objectsChanged(DrawingModel source, int index0, int index1) {
			modelChanged = true;			
		}

		public boolean isModelChanged() {
			return modelChanged;
		}

		public void setModelChanged(boolean modelChanged) {
			this.modelChanged = modelChanged;
		}
		
	}
	
	/**
	 * Returns change listener
	 * @return change listener
	 */
	public ModelChangeListener getChangeListener() {
		return changeListener;
	}
	
	/**
	 * Returns path of current document
	 * @return path of current document
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Sets path of current document
	 * @param filePath - path of current document
	 */
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}

	/**
	 * Returns drawing model
	 * @return drawing model
	 */
	public DrawingModel getModel() {
		return model;
	}
	
	/**
	 * Returns current state
	 * @return current state
	 */
	public static Tool getCurrentState() {
		return currentState;
	}
	
	/**
	 * Returns lines of a jvd file which represents 
	 * current picture
	 * @return lines of a jvd file
	 */
	public List<String> getJvdFile() {
		List<String> lines = new ArrayList<>();
		for(int i = 0; i<model.getSize(); i++) {
			lines.add(model.getObject(i).getJvdString());
		}
		
		return lines;
	}
	
	/**
	 * Starting point of program
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

}

package hr.fer.zemris.java.hw16.jdraw.actions;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jdraw.JVDraw;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Circle;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Line;

/**
 * Class which contains actions used to open, save and export document.
 * @author Josip Trbuscic
 *
 */
public class FileActions {
	private JVDraw jvdraw;
	/**
	 * Open file action
	 */
	public Action openFile;
	
	/**
	 * Save file action
	 */
	public Action saveFile;
	
	/**
	 * Save as new file action
	 */
	public Action saveFileAs;
	
	/**
	 * Export file action
	 */
	public Action export;

	/**
	 * Constructor
	 * @param jvdraw 
	 */
	public FileActions(JVDraw jvdraw) {
		this.jvdraw = jvdraw;
		
		initActions();
	}
	
	/**
	 * Initializes actions
	 */
	private void initActions() {
		openFile = new AbstractAction(){
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.NAME, "Open");
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(null,"jvd");
				fc.setFileFilter(filter);
				
				fc.setDialogTitle("Open file");
				if (fc.showOpenDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();
				
				if (!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(
							jvdraw, "File not readable",
							"Error", JOptionPane.ERROR_MESSAGE
							);
					return;
				}
				
				List<String> lines = null;
				try {
					lines = Files.readAllLines(filePath);
				} catch (IOException e1) {
				}
				
				List<GeometricalObject> objects = parseLines(lines);
				
				for(int i = 0, size = jvdraw.getModel().getSize() ; i<size ;i++) {
					jvdraw.getModel().remove(jvdraw.getModel().getObject(0));
				}
				Collections.reverse(objects);
				objects.forEach(o->jvdraw.getModel().add(o));
				jvdraw.setFilePath(filePath);
			}

			
		};
		
		
		saveFile =  new AbstractAction() {
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.NAME, "Save");
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Path path = jvdraw.getFilePath();

				if (path == null) {
					saveAs();
				} else {
					List<String> lines = jvdraw.getJvdFile();
					try {
						if(!path.toString().endsWith(".jvd")) {
							path = Paths.get(path + ".jvd");
						}
						Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING);
					} catch (IOException e1) {
					}
				}
				jvdraw.getChangeListener().setModelChanged(false);
			}
		};

		saveFileAs = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.NAME, "Save As");
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
				jvdraw.getChangeListener().setModelChanged(false);
			}
		};
		
		export = new AbstractAction(){
			private static final long serialVersionUID = 1L;

			{
				putValue(Action.NAME, "Export");
				putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
				putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton jpgButton = new JRadioButton("jpg");
				JRadioButton pngButton = new JRadioButton("png");
				JRadioButton gifButton = new JRadioButton("gif");
				
				jpgButton.setSelected(true);
				
				ButtonGroup buttonGroup = new ButtonGroup();
				buttonGroup.add(jpgButton);
				buttonGroup.add(pngButton);
				buttonGroup.add(gifButton);
				
				JPanel panel = new JPanel(new FlowLayout());
				panel.add(jpgButton);
				panel.add(pngButton);
				panel.add(gifButton);
				String extension = null; 
				if(JOptionPane.showConfirmDialog(jvdraw, panel,"Select extension" , JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
					return;
				}
				
				for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();

		            if (button.isSelected()) {
		            	extension = button.getText();
		            }
		        }

				JFileChooser fc = new JFileChooser();
				if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				Path filePath = fc.getSelectedFile().toPath();

				GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
				for(int i=jvdraw.getModel().getSize()-1; i>=0; i--) {
					jvdraw.getModel().getObject(i).accept(bbcalc);
				}
				Rectangle box = bbcalc.getBoundingBox();
				
				BufferedImage image = new BufferedImage(
					    box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
				);
				
				Graphics2D g = image.createGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, box.width, box.height);
				g.translate(-box.x, -box.y);
				
				GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
				
				for(int i=jvdraw.getModel().getSize()-1; i>=0; i--) {
					jvdraw.getModel().getObject(i).accept(painter);
				}
				g.dispose();
				File file = Paths.get(filePath+"."+extension).toFile();
				try {
					ImageIO.write(image, extension, file);
				} catch (IOException e1) {
				}
				
			}

		};
		
	}

	/**
	 * Method called if edited document should be saved as new file.
	 * @param m - document to save
	 */
	public void saveAs() {
		saveAsFileChooser.setDialogTitle("Save As");
		if (saveAsFileChooser.showSaveDialog(jvdraw) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		Path path = saveAsFileChooser.getSelectedFile().toPath();
		if(!path.toString().endsWith(".jvd")) {
			path = Paths.get(path + ".jvd");
		}
		
		jvdraw.setFilePath(path);
		List<String> lines = jvdraw.getJvdFile();
		
		try {
			Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				int result = JOptionPane.showConfirmDialog(jvdraw, "File already exists", "Choose",
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
	 * Parses lines of .jvd file and creates new geometric
	 * object from each parsed line
	 * @param lines - lines of .jvd file
	 * @return list of created objects
	 */
	private List<GeometricalObject> parseLines(List<String> lines) {
		List<GeometricalObject> objects = new LinkedList<>();
		
		for(String line : lines) {
			try {
				String[] parts = line.split("\\s+");
				String name = parts[0];
				switch(name) {
				case"LINE":
					objects.add(new Line(Integer.parseInt(parts[1]),
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							Integer.parseInt(parts[4]),
							new Color(Integer.parseInt(parts[5]),
									Integer.parseInt(parts[6]), 
									Integer.parseInt(parts[7])
									)
							));
					break;
				case"CIRCLE":
					objects.add(new Circle(Integer.parseInt(parts[1]),
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							new Color(Integer.parseInt(parts[4]),
									Integer.parseInt(parts[5]), 
									Integer.parseInt(parts[6])
									)
							));
					break;
				case"FCIRCLE":
					objects.add(new FilledCircle(Integer.parseInt(parts[1]),
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							new Color(Integer.parseInt(parts[4]),
									Integer.parseInt(parts[5]), 
									Integer.parseInt(parts[6])
									),
							new Color(Integer.parseInt(parts[7]),
									Integer.parseInt(parts[8]), 
									Integer.parseInt(parts[9])
									)
							));
					break;
				}
			}catch(Exception ignore) {
			}
		}
		return objects;
	}
	
}

package hr.fer.zemris.java.hw16.jdraw.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Swing component which offers user to change color 
 * and notifies all listeners when color is changed
 * @author Josip Trbuscic
 *
 */
public class JColorArea extends JComponent implements IColorProvider{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Current color
	 */
	private Color selectedColor;
	
	/**
	 * Old color
	 */
	private Color oldColor;
	
	private List<ColorChangeListener> listeners;
	
	/**
	 * Constructor
	 * @param selectedColor - selected color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		this.oldColor = selectedColor;
		
		this.addMouseListener(new ColorAreaMouseListener());
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(selectedColor);
        Insets ins = getInsets();
        g.fillRect(ins.left, ins.top, 15,15);
	}
	
	/**
	 * Mouse listener used to change set current and old color
	 * when new color is selected
	 * @author Josip Trbuscic
	 *
	 */
	private class ColorAreaMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			Color newColor = JColorChooser.showDialog(
                    JColorArea.this,
                    "Choose color",
                    selectedColor);
			if(newColor == null) return;
			
			oldColor = selectedColor;
			selectedColor = newColor;
			paintComponent(getGraphics());
			notifyListeners();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
		
	}
	
	/**
	 * notifies listeners when color is changed
	 */
	private void notifyListeners() {
		if(listeners == null) return;
		listeners.forEach(l->l.newColorSelected(this, oldColor, selectedColor));
	}
	
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if(listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(l);
		notifyListeners();
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
}

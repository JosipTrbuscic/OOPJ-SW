package hr.fer.zemris.java.hw16.jdraw.colors;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Swing component which displays information about current
 * background and foreground colors
 * @author Josip Trbuscic
 *
 */
public class JColorLabel extends JLabel implements ColorChangeListener{
	private static final long serialVersionUID = 1L;
	
	/**
	 * foreground color provider
	 */
	private IColorProvider fgColorProvider;
	
	/**
	 * background color provider
	 */
	private IColorProvider bgColorProvider;
	
	/**
	 * formated foreground color info
	 */
	private String fgText;
	
	/**
	 * formated background color info
	 */
	private String bgText;
	
	/**
	 * Constructor
	 * @param fgColorProvider
	 * @param bgColorProvider
	 */
	public JColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		updateFgText(fgColorProvider.getCurrentColor());
		updateBgText(bgColorProvider.getCurrentColor());
	}
	
	/**
	 * Updates foreground color info
	 * @param fg - foreground color
	 */
	private void updateFgText(Color fg) {
		fgText = String.format("Foreground color: (%d, %d, %d)",
								fg.getRed(), fg.getGreen(), fg.getBlue()
								);
		setText();
	}
	
	/**
	 * Updates background color info
	 * @param bg - background color
	 */
	private void updateBgText(Color bg) {
		bgText = String.format("BackGround color: (%d, %d, %d)",
								bg.getRed(), bg.getGreen(), bg.getBlue()
								);
		setText();
	}
	
	/**
	 * Sets component text
	 */
	private void setText() {
		setText(fgText + ", " + bgText);
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if(source == fgColorProvider) {
			updateFgText(newColor);
		} else {
			updateBgText(newColor);
		}
	}
}

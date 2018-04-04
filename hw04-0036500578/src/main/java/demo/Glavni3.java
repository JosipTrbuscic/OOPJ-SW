package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Program for creating L system which draws curve from configuration
 * specified in a file
 * 
 *@author Josip Trbuscic
 */
public class Glavni3 {
	
	/**
	 * Main method. Initializes L system
	 * @param args
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
		
	}
}

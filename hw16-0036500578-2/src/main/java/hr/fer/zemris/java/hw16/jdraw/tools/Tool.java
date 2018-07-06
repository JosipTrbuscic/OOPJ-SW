package hr.fer.zemris.java.hw16.jdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Interface which defines methods used to help user 
 * in object painting by reacting to mouse movements
 * @author Josip Trbsucic
 *
 */
public interface Tool {
	
	/**
	 * Method called on mouse press
	 * @param e
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * Method called on mouse release
	 * @param e
	 */
	public void mouseReleased(MouseEvent e);
	
	/**
	 * Method called on mouse click
	 * @param e
	 */
	public void mouseClicked(MouseEvent e);
	
	/**
	 * Method called when mouse is moved
	 * @param e
	 */
	public void mouseMoved(MouseEvent e);
	
	/**
	 * Method called when mouse is dragged
	 * @param e
	 */
	public void mouseDragged(MouseEvent e);
	
	/**
	 * Paints object from current info
	 * @param g2d
	 */
	public void paint(Graphics2D g2d);
}

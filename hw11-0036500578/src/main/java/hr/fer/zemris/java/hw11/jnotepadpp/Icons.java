package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * This class contains icons used in JNotepad++
 * @author Josip Trbuscic
 *
 */
public class Icons {
	/**
	 * Icon which indicates that document has not been edited
	 */
	public static final ImageIcon GREEN_CIRCLE= getIcon("icons/green_circle.png");
	
	/**
	 * Icon which indicates that document has been edited
	 */
	public static final ImageIcon RED_CIRCLE= getIcon("icons/red_circle.png");
	
	/**
	 * Returns icon from given path
	 * @param path - path to icon file
	 * @return icon from given path
	 */
	private static ImageIcon getIcon(String path) {
		InputStream is = Icons.class.getResourceAsStream(path);
		if(is==null) {
			return null;
		}
		byte[] bytes = null;
		try {
			bytes = is.readAllBytes();
			is.close();
		} catch (IOException e) {
			//ignorable
		}
		return new ImageIcon(bytes); 
	}
}

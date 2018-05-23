package hr.fer.zemris.java.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

public class Icons {
	public static final ImageIcon GREEN_CIRCLE= getIcon("/src/main/resources/hr/fer/zemris/java/hw11/jnotepadpp/icons/green_circle.png");
	public static final ImageIcon RED_CIRCLE= getIcon("icons/red_circle.png");
	
	private static ImageIcon getIcon(String path) {
		InputStream is = Icons.class.getResourceAsStream(path);
		if(is==null) {
			System.out.println("Error");
			System.out.println(path);
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

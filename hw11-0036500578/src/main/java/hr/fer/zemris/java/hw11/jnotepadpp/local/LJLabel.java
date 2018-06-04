package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * This class is extension of {@link JLabel} which 
 * sets its title based on localization settings 
 * @author Josip Trbuscic
 *
 */
public class LJLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param key - key
	 * @param lp - localization provider
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		setText(lp.getString(key));
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
				
			}
		});
	}
}

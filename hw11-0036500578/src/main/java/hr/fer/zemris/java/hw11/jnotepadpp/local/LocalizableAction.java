package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * {@link AbstractAction} extension which modifies 
 * its properties based on localization settings
 * @author Josip Trbuscic
 *
 */
public abstract class LocalizableAction extends AbstractAction{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param key - name key
	 * @param shortDescKey - short description key
	 * @param lp - Localization provider
	 */
	public LocalizableAction(String key, String shortDescKey, ILocalizationProvider lp) {
		putValue(NAME, lp.getString(key));
		putValue(SHORT_DESCRIPTION, lp.getString(shortDescKey));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
				putValue(SHORT_DESCRIPTION, lp.getString(shortDescKey));

				
			}
		});
	}
}

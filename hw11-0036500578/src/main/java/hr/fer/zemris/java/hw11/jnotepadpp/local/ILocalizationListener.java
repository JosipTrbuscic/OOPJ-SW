package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Localization listener interface. 
 * Every listener is notified when localization has changed
 * @author Josip Trbuscic
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Method executed when localization has changed
	 */
	void localizationChanged();
}

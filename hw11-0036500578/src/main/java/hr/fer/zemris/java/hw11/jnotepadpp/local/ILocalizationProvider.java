package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Localization provider interface. Localization providers
 * are used to return value based on given key and current
 * localization.
 * @author Josip Trbuscic
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Returns value for given key
	 * @param key - key
	 * @return value for given key
	 */
	String getString(String key);
	
	/**
	 * Registers specified listener if it wasn't already registered
	 * @param l - listener
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Unregisters specified listener if it was registered
	 * @param l - listener to remove
	 */
	void removeLocalizationListener(ILocalizationListener l);
}

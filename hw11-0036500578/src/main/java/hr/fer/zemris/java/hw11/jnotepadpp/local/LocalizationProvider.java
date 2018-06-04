package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class which is used to store localization settings and 
 * notifying observers about language change
 * @author Josip Trbuscic
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{
	/**
	 * Current language
	 */
	private String language;
	
	/**
	 * Current locale resources
	 */
	private ResourceBundle bundle;
	
	/**
	 * Instance of this class
	 */
	private static LocalizationProvider instance;
	
	/**
	 * Constructor
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	/**
	 * Returns instance of this class
	 * @return instance of this class
	 */
	public static LocalizationProvider getInstance() {
		if(instance == null) {
			instance = new LocalizationProvider();
		}
		
		return instance;
	}
	
	/**
	 * Sets given language to current one
	 * @param language - language to set
	 */
	public void setLanguage(String language){
		if(language.equals(this.language)) return;
		this.language = language;
		bundle = ResourceBundle.getBundle(
				LocalizationProvider.class.getPackage().getName()+".translation_"+language
				, Locale.forLanguageTag(this.language)
		);
		
		fire();
	}
	
	/**
	 * Returns value based on given key and current language
	 */
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}

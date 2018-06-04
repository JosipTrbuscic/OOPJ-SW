package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class used as decorator for some other {@link ILocalizationProvider}
 * @author Josip Trbuscic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	/**
	 * Decorated provider
	 */
	private ILocalizationProvider parent;
	
	/**
	 * Indicator if provider is connected
	 */
	private boolean connected;
	
	/**
	 * Listener
	 */
	private ILocalizationListener listener;
	
	/**
	 * Constructor
	 * @param parent - decorated provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}
	
	/**
	 * Disconnects listener from localization provider
	 */
	public void disconnect() {
		if(connected) {
			parent.removeLocalizationListener(listener);
			connected = false;
		}
	}
	
	/**
	 * Connects listener to localization provider
	 */
	public void connect() {
		if(!connected) {
			listener = ()->fire();
			parent.addLocalizationListener(listener);
			connected = true;
		}
	}

}

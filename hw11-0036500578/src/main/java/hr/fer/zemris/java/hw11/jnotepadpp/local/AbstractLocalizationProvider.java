package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class which manages listeners and 
 * notifies them about localization change
 * @author Josip Trbuscic
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	/**
	 * Listeners
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Constructor
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	/**
	 * Registers specified listener if it wasn't already registered
	 * @param l - listener
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		if(listeners.contains(l)) return;
		listeners.add(l);
	}
	/**
	 * 
	 * Unregisters specified listener if it was registered
	 * @param l - listener to remove
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
		
	}
	
	/**
	 * Notifies all listeners about localization change
	 */
	public void fire() {
		listeners.forEach(a->a.localizationChanged());
	}

}

package jp.co.future.eclipse.usqlfmt;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer.CaseType;
import jp.co.future.eclipse.usqlfmt.python.PyEngine;

/**
 * The activator class controls the plug-in life cycle
 *
 * @author hoshi
 */
public class UroborosqlFormatterPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "jp.co.future.eclipse.usqlfmt"; //$NON-NLS-1$

	// The shared instance
	private static UroborosqlFormatterPlugin plugin;

	private PyEngine engine;

	/**
	 * The constructor
	 */
	public UroborosqlFormatterPlugin() {
	}

	/**
	 * get PyEngine instance.
	 * @return PyEngine
	 */
	public PyEngine getPyEngine() {
		if (engine == null) {
			engine = new PyEngine();
		}
		return engine;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// For backward compatibility
		// Read the preference file and if there are privious version settings in the
		// file, rewrite the setings to new settings to keep compatibility.
		IPreferenceStore store = UroborosqlFormatterPlugin.getDefault().getPreferenceStore();
		String configUseUppercase = store.getString(UroborosqlFormatterPreferenceInitializer.USE_UPPERCASE);
		String configHasBackwardCompatibilityDone = store
				.getString(UroborosqlFormatterPreferenceInitializer.HAS_BACKWARD_COMPATIBILITY_DONE);

		if (configUseUppercase != null) {
			backwardCompatibility(store, configUseUppercase, configHasBackwardCompatibilityDone);
		}
	}

	/**
	 * For backward compatibility
	 * Read the .prefs file and if the .prefs file contains the previous version settings,
	 * rewrite the setings to new settings to keep compatibility.
	 *
	 * @param store IPreferenceStore
	 * @param configUseUppercase The configuration setting of UseUppercase
	 * @param configHasBackwardCompatibilityDone The configuration setting of backward compatibility
	 */
	public void backwardCompatibility(IPreferenceStore store, String configUseUppercase,
			String configHasBackwardCompatibilityDone) {
		if (configHasBackwardCompatibilityDone.equals("false")) {
			if (configUseUppercase.equals("false")) {
				store.setValue(UroborosqlFormatterPreferenceInitializer.CASE, CaseType.noChange.name());
				store.setValue(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE, CaseType.noChange.name());
			}
			store.setValue(UroborosqlFormatterPreferenceInitializer.HAS_BACKWARD_COMPATIBILITY_DONE, "true");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static UroborosqlFormatterPlugin getDefault() {
		return plugin;
	}

}

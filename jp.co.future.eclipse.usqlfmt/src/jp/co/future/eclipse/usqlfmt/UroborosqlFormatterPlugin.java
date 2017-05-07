package jp.co.future.eclipse.usqlfmt;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

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

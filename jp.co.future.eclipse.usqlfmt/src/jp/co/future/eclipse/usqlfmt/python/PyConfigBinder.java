package jp.co.future.eclipse.usqlfmt.python;

import org.eclipse.jface.preference.IPreferenceStore;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer.CommentSyntaxType;

/**
 * Binding plugin settings to python.
 *
 * @author hoshi
 */
public class PyConfigBinder {
	/**
	 * Binding plugin settings to python.
	 *
	 * @param engine
	 *            PyEngine
	 */
	public void bind(PyEngine engine) {
		IPreferenceStore store = UroborosqlFormatterPlugin.getDefault().getPreferenceStore();

		engine.eval("config = LocalConfig()");
		if (!store.getBoolean(UroborosqlFormatterPreferenceInitializer.USE_UPPERCASE)) {
			engine.eval("config.set_uppercase(False)");
		}
		if (CommentSyntaxType.Doma2.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.COMMENT_SYNTAX_TYPE))) {
			engine.eval("config.set_commentsyntax(Doma2CommentSyntax())");
		}
		if (store.getBoolean(UroborosqlFormatterPreferenceInitializer.USE_BACKSLASH)) {
			engine.eval("uroborosqlfmt.config.glb.escape_sequence_u005c = True");
		}
	}
}

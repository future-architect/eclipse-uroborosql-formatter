package jp.co.future.eclipse.usqlfmt.python;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer.CaseType;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer.CommentSyntaxType;

import org.eclipse.jface.preference.IPreferenceStore;

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
		if (CaseType.lower.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.CASE))) {
			engine.eval("config.set_case(lower)");
		} else if (CaseType.upper.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.CASE))) {
			engine.eval("config.set_case(upper)");
		} else if (CaseType.capitalize.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.CASE))) {
			engine.eval("config.set_case(capitalize)");
		}
		if (CaseType.lower.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE))) {
			engine.eval("config.set_reserved_case(lower)");
		} else if (CaseType.upper.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE))) {
			engine.eval("config.set_reserved_case(upper)");
		} else if (CaseType.capitalize.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE))) {
			engine.eval("config.set_reserved_case(capitalize)");
		}
		if (store.getString(UroborosqlFormatterPreferenceInitializer.INPUT_RESERVED_WORDS) != null) {
			engine.eval(store.getString(UroborosqlFormatterPreferenceInitializer.INPUT_RESERVED_WORDS));
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

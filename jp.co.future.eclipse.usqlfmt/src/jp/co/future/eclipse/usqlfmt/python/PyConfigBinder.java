package jp.co.future.eclipse.usqlfmt.python;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer.CaseType;
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
		if (CaseType.LOWER.name().equals(store.getString(UroborosqlFormatterPreferenceInitializer.CASE))) {
			engine.eval("config.set_case('lower')");
		} else if (CaseType.UPPER.name().equals(store.getString(UroborosqlFormatterPreferenceInitializer.CASE))) {
			engine.eval("config.set_case('upper')");
		} else if (CaseType.CAPITALIZE.name().equals(store.getString(UroborosqlFormatterPreferenceInitializer.CASE))) {
			engine.eval("config.set_case('capitalize')");
		}
		if (CaseType.LOWER.name().equals(store.getString(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE))) {
			engine.eval("config.set_reserved_case('lower')");
		} else if (CaseType.UPPER.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE))) {
			engine.eval("config.set_reserved_case('upper')");
		} else if (CaseType.CAPITALIZE.name()
				.equals(store.getString(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE))) {
			engine.eval("config.set_reserved_case('capitalize')");
		}
		if (store.getString(UroborosqlFormatterPreferenceInitializer.RESERVED_WORDS) != null) {
			String inputReservedWords = store.getString(UroborosqlFormatterPreferenceInitializer.RESERVED_WORDS);
			String[] reservedWords = inputReservedWords.split(",", 0);
			List<String> reservedWordsList = new ArrayList<String>();
			for (int i = 0; i < reservedWords.length; i++) {
				reservedWordsList.add(reservedWords[i]);
			}
			engine.put("input_reserved_words", reservedWordsList);
			engine.eval("config.set_input_reserved_words(input_reserved_words)");
		}
		if (CommentSyntaxType.Doma2.name().equals(store.getString(UroborosqlFormatterPreferenceInitializer.COMMENT_SYNTAX_TYPE))) {
			engine.eval("config.set_commentsyntax(Doma2CommentSyntax())");
		}
		if (store.getBoolean(UroborosqlFormatterPreferenceInitializer.USE_BACKSLASH)) {
			engine.eval("uroborosqlfmt.config.glb.escape_sequence_u005c = True");
		}
	}

}

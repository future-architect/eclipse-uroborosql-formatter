package jp.co.future.eclipse.usqlfmt.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;

/**
 * initalize preferences.
 *
 * @author hoshi
 */
public class UroborosqlFormatterPreferenceInitializer extends AbstractPreferenceInitializer {
	/**
	 * Provides SQL comment syntax type.
	 */
	public enum CommentSyntaxType {
		/** uroboroSQL, S2Dao, S2JDBC, DBFlute. */
		Uroborosql,
		/** DOMA2 */
		Doma2
	}

	/** Convert reserved words and identifiers to upper case */
	public static final String USE_UPPERCASE = "useUppercase";
	/** Using backslash escape sequences */
	public static final String USE_BACKSLASH = "useBackslash";
	/** Comment syntax type */
	public static final String COMMENT_SYNTAX_TYPE = "commentSyntaxType";

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = UroborosqlFormatterPlugin.getDefault().getPreferenceStore();
		store.setDefault(USE_UPPERCASE, true);
		store.setDefault(USE_BACKSLASH, false);
		store.setDefault(COMMENT_SYNTAX_TYPE, CommentSyntaxType.Uroborosql.name());
	}

}

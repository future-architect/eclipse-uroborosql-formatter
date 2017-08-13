package jp.co.future.eclipse.usqlfmt.preferences;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

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

	public enum CaseType {
		lower,
		upper,
		capitalize
	}

	/** Convert words to a specific case*/
	public static final String CASE = "case";
	/** Convert words to a specific case*/
	public static final String RESERVED_CASE = "reservedCase";
	/** Convert words to a specific case*/
	public static final String INPUT_RESERVED_WORDS = "inputReservedCase";
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
		store.setDefault(CASE, CaseType.upper.name());
		store.setDefault(RESERVED_CASE, CaseType.upper.name());
		store.setDefault(INPUT_RESERVED_WORDS, "");
		store.setDefault(USE_BACKSLASH, false);
		store.setDefault(COMMENT_SYNTAX_TYPE, CommentSyntaxType.Uroborosql.name());
	}

}

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

	public enum CaseType {
		LOWER,
		UPPER,
		CAPITALIZE,
		NOCHANGE
	}

	// for backward compatibility start
	/** Convert reserved words and identifiers to upper case */
	public static final String USE_UPPERCASE = "useUppercase";
	public static final String HAS_BACKWARD_COMPATIBILITY_DONE = "hasBackwardCompatibilityDone";
	// backward compatibility end

	/** Convert words to a specific case */
	public static final String CASE = "case";
	/** Convert words to a specific case */
	public static final String RESERVED_CASE = "reservedCase";
	/** Convert words to a specific case */
	public static final String RESERVED_WORDS = "inputReservedWord";
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
		store.setDefault(HAS_BACKWARD_COMPATIBILITY_DONE, false);
		store.setDefault(CASE, CaseType.UPPER.name());
		store.setDefault(RESERVED_CASE, CaseType.UPPER.name());
		store.setDefault(RESERVED_WORDS, "");
		store.setDefault(USE_BACKSLASH, false);
		store.setDefault(COMMENT_SYNTAX_TYPE, CommentSyntaxType.Uroborosql.name());
	}

}

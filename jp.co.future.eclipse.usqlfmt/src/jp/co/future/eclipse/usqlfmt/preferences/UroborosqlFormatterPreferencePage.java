package jp.co.future.eclipse.usqlfmt.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer.CommentSyntaxType;

/**
 * uroboroSQL formatter plugin preferences page.
 *
 * @author hoshi
 */

public class UroborosqlFormatterPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public UroborosqlFormatterPreferencePage() {
		super(GRID);
		setPreferenceStore(UroborosqlFormatterPlugin.getDefault().getPreferenceStore());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(UroborosqlFormatterPreferenceInitializer.USE_UPPERCASE,
				"&Convert reserved words and identifiers to upper case", getFieldEditorParent()));
		addField(new BooleanFieldEditor(UroborosqlFormatterPreferenceInitializer.USE_BACKSLASH,
				"&Using backslash escape sequences", getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(UroborosqlFormatterPreferenceInitializer.COMMENT_SYNTAX_TYPE,
				"Comment syntax type", 1,
				new String[][] {
						new String[] { CommentSyntaxType.Uroborosql.name(), CommentSyntaxType.Uroborosql.name() },
						new String[] { CommentSyntaxType.Doma2.name(), CommentSyntaxType.Doma2.name() } },
				getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}
package jp.co.future.eclipse.usqlfmt.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;
import jp.co.future.eclipse.usqlfmt.preferences.UroborosqlFormatterPreferenceInitializer.CaseType;
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
	@Override
	public void createFieldEditors() {
		addField(new RadioGroupFieldEditor(UroborosqlFormatterPreferenceInitializer.CASE,
				"Specify case", 1,
				new String[][] {
						new String[] { CaseType.UPPER.name(), CaseType.UPPER.name() },
						new String[] { CaseType.LOWER.name(), CaseType.LOWER.name() },
						new String[] { CaseType.CAPITALIZE.name(), CaseType.CAPITALIZE.name() },
						new String[] { CaseType.NOCHANGE.name(), CaseType.NOCHANGE.name() }
				}, getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE,
				"Specify reserved words case", 1,
				new String[][] {
						new String[] { CaseType.UPPER.name(), CaseType.UPPER.name() },
						new String[] { CaseType.LOWER.name(), CaseType.LOWER.name() },
						new String[] { CaseType.CAPITALIZE.name(), CaseType.CAPITALIZE.name() },
						new String[] { CaseType.NOCHANGE.name(), CaseType.NOCHANGE.name() }
				}, getFieldEditorParent()));
		addField(new StringFieldEditor(UroborosqlFormatterPreferenceInitializer.RESERVED_WORDS,
				"Input reserved words list(comma separated)", getFieldEditorParent()));
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
	@Override
	public void init(IWorkbench workbench) {
	}

}
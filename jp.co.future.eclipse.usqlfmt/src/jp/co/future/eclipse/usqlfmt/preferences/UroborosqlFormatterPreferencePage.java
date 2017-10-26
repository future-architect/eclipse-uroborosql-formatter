package jp.co.future.eclipse.usqlfmt.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
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

	StringFieldEditor reservedWordsEditor;

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
						new String[] { "UPPER", CaseType.UPPER.name() },
						new String[] { "lower", CaseType.LOWER.name() },
						new String[] { "Capitalize", CaseType.CAPITALIZE.name() },
						new String[] { "nochange", CaseType.NOCHANGE.name() }
				}, getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE,
				"Specify reserved words case", 1,
				new String[][] {
						new String[] { "UPPER", CaseType.UPPER.name() },
						new String[] { "lower", CaseType.LOWER.name() },
						new String[] { "Capitalize", CaseType.CAPITALIZE.name() },
						new String[] { "nochange", CaseType.NOCHANGE.name() }
				}, getFieldEditorParent()));

		reservedWordsEditor = new StringFieldEditor(UroborosqlFormatterPreferenceInitializer.RESERVED_WORDS,
				"Input reserved words list(comma separated)", getFieldEditorParent());
		addField(reservedWordsEditor);
		addField(new BooleanFieldEditor(UroborosqlFormatterPreferenceInitializer.USE_BACKSLASH,
				"&Using backslash escape sequences", getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(UroborosqlFormatterPreferenceInitializer.COMMENT_SYNTAX_TYPE,
				"Comment syntax type", 1,
				new String[][] {
						new String[] { CommentSyntaxType.Uroborosql.name(), CommentSyntaxType.Uroborosql.name() },
						new String[] { CommentSyntaxType.Doma2.name(), CommentSyntaxType.Doma2.name() } },
				getFieldEditorParent()));
	}

	/**
	 * Enable or disable the reserved word section deprnds on the user input.
	 * if the user input is "NOCHANGE" in the reserved case section, the reserved word section is disabled,
	 * otherwise the option is enabled.
	 *
	 * @param PropertyChangeEvent event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getSource() instanceof RadioGroupFieldEditor){
			RadioGroupFieldEditor editor = (RadioGroupFieldEditor) event.getSource();
			if(editor.getPreferenceName().equals(UroborosqlFormatterPreferenceInitializer.RESERVED_CASE)) {
				if (CaseType.NOCHANGE.name().equals(event.getNewValue().toString())) {
					this.reservedWordsEditor.setEnabled(false, getFieldEditorParent());
				}else {
					this.reservedWordsEditor.setEnabled(true, getFieldEditorParent());
				}
			}
		}
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
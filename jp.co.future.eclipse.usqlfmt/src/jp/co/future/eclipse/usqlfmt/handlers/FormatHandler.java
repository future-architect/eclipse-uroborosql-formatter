package jp.co.future.eclipse.usqlfmt.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;
import jp.co.future.eclipse.usqlfmt.python.PyConfigBinder;
import jp.co.future.eclipse.usqlfmt.python.PyEngine;

/**
 * Format handler extends AbstractHandler, an IHandler base class.
 *
 * @author hoshi
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class FormatHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ITextEditor editor = (ITextEditor) window.getActivePage().getActiveEditor();
		if (editor == null) {
			return null;
		}

		String workSql = null;
		final boolean isSelection;
		if (editor.getSelectionProvider().getSelection() instanceof TextSelection) {
			workSql = ((TextSelection) editor.getSelectionProvider().getSelection()).getText();
		}
		if (workSql == null || "".equals(workSql)) {
			workSql = editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
			isSelection = false;
		} else {
			isSelection = true;
		}

		final String sql = workSql;

		// format async
		Thread thread = new Thread("uroboroSQL Format Thread") {
			public void run() {
				PyEngine engine = UroborosqlFormatterPlugin.getDefault().getPyEngine();
				engine.put("sql", sql);

				// bind python config
				PyConfigBinder binder = new PyConfigBinder();
				binder.bind(engine);

				window.getWorkbench().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						try {
							engine.eval("f = uroborosqlfmt.format_sql(sql, config)");
							String fmtText = engine.get("f");
							IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());

							if (isSelection) {
								TextSelection selection = (TextSelection) editor.getSelectionProvider().getSelection();
								int offset = selection.getOffset();
								int length = selection.getLength();
								doc.replace(offset, length, fmtText);
								editor.selectAndReveal(offset, fmtText.length());
							} else {
								doc.set(fmtText);
							}
						} catch (RuntimeException | BadLocationException ex) {
							UroborosqlFormatterPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,
									UroborosqlFormatterPlugin.PLUGIN_ID, "Format failed!", ex));
							MessageDialog.openError(window.getShell(), "Eclipse uroboroSQL, Formatter",
									"Format failed!");
						}
					}
				});
			}
		};
		thread.setDaemon(true);
		thread.start();

		return null;
	}
}

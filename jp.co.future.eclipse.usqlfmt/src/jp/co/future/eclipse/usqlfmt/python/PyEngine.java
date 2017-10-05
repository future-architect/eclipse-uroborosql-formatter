package jp.co.future.eclipse.usqlfmt.python;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.core.runtime.FileLocator;
import org.python.core.PyBaseException;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.jsr223.PyScriptEngine;
import org.python.util.PythonInterpreter;

import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterException;
import jp.co.future.eclipse.usqlfmt.UroborosqlFormatterPlugin;

/**
 * Adapt to python engine.
 *
 * @author hoshi
 */
public class PyEngine implements AutoCloseable {
	private final ScriptEngine engine;// = null;
	private final PythonInterpreter interp;

	public PyEngine() {
		String pythonPath = getPythonPath();
		String enginePath = Paths.get(pythonPath).getParent().getParent().resolve("lib/jython-standalone-2.7.0.jar/Lib").toString();
		Properties props = new Properties();
		props.put("python.home","lib");
		props.put("python.path", pythonPath);
		props.put("python.console.encoding", "UTF-8");
		props.put("python.security.respectJavaAccessibility", "false");
		props.put("python.import.site","false");

		PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
		interp = new PythonInterpreter(null, new PySystemState());
		interp.getSystemState().path.append(new PyString(enginePath));
		engine = new ScriptEngineManager().getEngineByName("python");
		PyEngine.eval(engine,
			"import uroborosqlfmt",
			"from uroborosqlfmt import api",
			"from uroborosqlfmt.config import LocalConfig",
			"from uroborosqlfmt.commentsyntax import Doma2CommentSyntax");
	}

	public <T> T eval(String script) {
		return eval(engine, script);
	}

	void eval(String... scripts) {
		eval(engine, scripts);
	}

	public void put(String key, String value) {
		engine.put(key, value);
	}

	public <T> void put(String key, List<T> value) {
		engine.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) engine.get(key);
	}

	static void eval(ScriptEngine engine, String... scripts) {
		for (String script : scripts) {
			eval(engine, script);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T eval(ScriptEngine engine, String script) {
		try {
			return (T) engine.eval(script);
		} catch (ScriptException e) {
			throw adjustException(e, script);
		}
	}

	private static RuntimeException adjustException(ScriptException scriptException, String... scripts) {
		Throwable cause = scriptException.getCause();
		if (!(cause instanceof PyException)) {
			if (scripts.length == 0) {
				return new IllegalStateException(scriptException);
			} else {
				return new IllegalStateException(
					scriptException.getMessage() + "\nscript:" + String.join("\n", scripts), scriptException);
			}
		}

		PyException e = (PyException) cause;

		PyObject o = e.value.getDict();
		if (!(o instanceof PyStringMap)) {
			return e;
		}
		PyStringMap m = (PyStringMap) o;

		String message;
		if (e.value instanceof PyBaseException) {
			message = String.valueOf(((PyBaseException) e.value).getMessage());
		} else {
			message = scriptException.getMessage();
		}

		PyObject tlist = m.getMap().get("tlist");
		PyObject trace = m.getMap().get("trace");

		return new UroborosqlFormatterException(message, String.valueOf(tlist), String.valueOf(trace), e);
	}

	@Override
	public void close() {
		if (engine instanceof PyScriptEngine) {
			((PyScriptEngine) engine).close();
		}
	}

	private static String getPythonPath() {
		try {
			URL root = UroborosqlFormatterPlugin.getDefault().getBundle().getEntry("resources/python");
			return Paths.get(FileLocator.toFileURL(root).toURI()).normalize().toString();
		} catch (URISyntaxException | IOException e) {
			throw new IllegalStateException(e);
		}
	}
}

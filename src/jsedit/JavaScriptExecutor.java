/**
 * 
 */
package jsedit;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

/**
 * @author karasing
 * 
 */
public class JavaScriptExecutor {

	/**
	 * @param javaScriptEditorPlugin 
	 * @param script
	 */
	public static void execute(JavaScriptEditorPlugin javaScriptEditorPlugin, String script) {
		Context context = Context.enter();
		try {
			ScriptableObject scope = context.initStandardObjects();
			Object result = context.evaluateString(scope, script, "", 1, null);
			javaScriptEditorPlugin.print(result);
		} finally {
			Context.exit();
		}
	}

}

/**
 * 
 */
package jsedit.rhino.tests;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import junit.framework.TestCase;

/**
 * @author karasing
 * 
 */
public class ExecutionTest extends TestCase {
	public void testSimpleExecution() throws Exception {
		Context context = Context.enter();
		try{
			ScriptableObject scope = context.initStandardObjects();
			String script = "Math.cos(Math.PI)";
			Object result = context.evaluateString(scope, script, "", 1, null);
			System.out.println(result);
		}finally{
			Context.exit();
		}
	}
}

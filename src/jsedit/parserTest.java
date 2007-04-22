package jsedit;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.ScriptOrFnNode;

import junit.framework.TestCase;

public class parserTest extends TestCase implements ErrorReporter {
	private String myfunction = "function(){\n" + "alert('hello');\n" + "";

	public void testname() throws Exception {
		
		Parser parser = new Parser(new CompilerEnvirons(),this);
		ScriptOrFnNode node = null;
		try{
		 node = parser.parse(myfunction, "", 1);
		}catch(RhinoException e){
			e.printStackTrace();
		}
		assertNotNull(node);
		
		System.out.println(node.getType());
		System.out.println(node.getFunctionCount());
		System.out.println(node.getEndLineno());
		System.out.println(node.toStringTree(node));
	}

	public void error(String message, String sourceName, int line,
			String lineSource, int lineOffset) {
	}

	public EvaluatorException runtimeError(String message, String sourceName,
			int line, String lineSource, int lineOffset) {
		return new  EvaluatorException(message,sourceName,line,lineSource,lineOffset);
		
	}

	public void warning(String message, String sourceName, int line,
			String lineSource, int lineOffset) {
	}
}

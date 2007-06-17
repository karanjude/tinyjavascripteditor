package jsedit.editors;

import org.eclipse.jface.viewers.LabelProvider;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

public class JavaScriptLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		String result = "";
		try {
			if (element == null)
				return "";
			Node node = (Node) element;
			if (node.getType() == Token.VAR) {
				result = node.getFirstChild().getString();
			} else if (node.getType() == Token.FUNCTION) {
				if (node instanceof FunctionNode) {
					FunctionNode functionNode = (FunctionNode) node;
					result = functionNode.getFunctionName();
				} else
					result = node.getString();
			} else if (node.getType() == Token.EXPR_RESULT) {
				result = node.getFirstChild().getString();
			} else if (node.getType() == Token.EXPR_VOID) {
				result = node.getFirstChild().getString();
			}
		} catch (Exception e) {

		}
		return result;
	}

}

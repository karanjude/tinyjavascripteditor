package jsedit.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.ScriptOrFnNode;
import org.mozilla.javascript.Token;

public class JavaScriptContentProvider implements ITreeContentProvider {

	private ScriptOrFnNode node;

	public Object[] getChildren(Object parentElement) {
		Node parent = (Node) parentElement;
		List<Node> nodes = new ArrayList<Node>();
		try {
			for (Node n = parent.getFirstChild(); n != null; n = n.getNext()) {
				if (n.getType() == Token.BLOCK)
					n = n.getFirstChild();
				if (n.getType() == Token.FUNCTION) {
					Node functionNode = n;
					FunctionNode fnNode = (FunctionNode) parent;
					for (int i = 0; i < fnNode.getFunctionCount(); i++) {
						if (fnNode.getFunctionNode(i).getFunctionName() == functionNode
								.getString()) {
							nodes.add(fnNode.getFunctionNode(i));
						}
					}
				} else if (n.getType() == Token.RETURN) {
					continue;
				} else
					nodes.add(n);
			}
		} catch (Exception e) {
		}
		return nodes.toArray(new Node[0]);
	}

	private Node resolve(Node n) {
		if (n.getType() == Token.BLOCK) {
			return resolveBlock(n);
		}
		return n;
	}

	private Node resolveBlock(Node n) {
		n = n.getFirstChild();
		if (n.getType() == Token.EXPR_VOID)
			return resolveVoidExpression(n);
		else if (n.getType() == Token.VAR)
			return n;
		return null;
	}

	private Node resolveVoidExpression(Node n) {
		n = n.getFirstChild();
		if (n.getType() == Token.NAME)
			return n;
		return null;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		Node parent = (Node) element;
		int count = 0;
		if (parent.getType() == Token.VAR)
			return false;
		else if (parent.getType() == Token.FUNCTION) {
			return parent.getFirstChild() != null ? resolve(parent
					.getFirstChild()) != null : false;
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		List<Node> nodes = new ArrayList<Node>();
		for (Node n = node.getFirstChild(); n != null; n = n.getNext()) {
			if (n.getType() == Token.FUNCTION) {
				Node functionNode = n;
				for (int i = 0; i < node.getFunctionCount(); i++) {
					if (node.getFunctionNode(i).getFunctionName() == functionNode
							.getString()) {
						nodes.add(node.getFunctionNode(i));
					}
				}
			} else
				nodes.add(n);
		}
		return nodes.toArray(new Node[0]);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		TreeViewer v = (TreeViewer) viewer;
		if (newInput != null) {
			node = (ScriptOrFnNode) newInput;
		}
	}

}

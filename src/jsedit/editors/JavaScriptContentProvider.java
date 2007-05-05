package jsedit.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.ScriptOrFnNode;

public class JavaScriptContentProvider implements ITreeContentProvider {

	private ScriptOrFnNode node;

	public Object[] getChildren(Object parentElement) {
		System.out.println("getChildren");
		return null;
	}

	public Object getParent(Object element) {
		System.out.println("getParenr");
		return null;
	}

	public boolean hasChildren(Object element) {
		System.out.println("fksdhfjs");
		return false;
	}

	public Object[] getElements(Object inputElement) {
		System.out.println("childrennnn...");
		List<Node> nodes = new ArrayList<Node>();
		for(Node n = node.getFirstChild(); n != node.getLastChild();n = n.getNext()){
			nodes.add(n);
		}
		return nodes.toArray(new Node[]{});
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		TreeViewer v = (TreeViewer) viewer;
		
		System.out.println("input changed");
		
		if(newInput != null){
			node = (ScriptOrFnNode)newInput; 
		}
	}

}

package jsedit.editors;

import jsedit.JavaScriptEditorPlugin;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilerExtension;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.ScriptOrFnNode;
import org.mozilla.javascript.Token;

public class JavaScriptReconcilingStrategy implements IReconcilingStrategy {

	private IDocument document;
	private ErrorReporter errorReporter;

	public JavaScriptReconcilingStrategy(ErrorReporter javaScriptEditor) {
		this.errorReporter = javaScriptEditor;
	}

	public void reconcile(IRegion partition) {
		Parser parser = new Parser(new CompilerEnvirons(), errorReporter);
		try {
			ScriptOrFnNode node = parser.parse(this.document.get(), null, 0);
			//print(node);
			((JavaScriptEditor)errorReporter).clearProblems();
		} catch (EvaluatorException e) {
			
		}
	}

	private void print(ScriptOrFnNode node){
		System.out.println("script.. " + " : " + node.getLineno());
		printAllNodes(node);
	}
	
	private void print(FunctionNode node){
		System.out.println("script.. " + " : " + node.getLineno());
		printAllNodes(node);
	}
	
	private void printAllNodes(Node n){
		for(Node nn = n.getFirstChild(); nn != null; nn = nn.getNext()){
			print(nn);
		}
	}
	
	private void print(Node node) {
		if(node.getType() == Token.VAR)
			System.out.println("var..." + " : " + node.getLineno());
		if(node.getType() == Token.NAME)
			System.out.println("name..." + " : " + node.getLineno());
		if(node.hasChildren()){
			Node n = node.getFirstChild();
			do{
				print(n);
				n = n.getNext();
			}while(n != null);
		}

	}

	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
	}

	public void setDocument(IDocument document) {
		this.document = document;
	}

}

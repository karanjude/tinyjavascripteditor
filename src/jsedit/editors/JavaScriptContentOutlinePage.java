package jsedit.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.ScriptOrFnNode;
import org.mozilla.javascript.Token;

public class JavaScriptContentOutlinePage extends ContentOutlinePage {

	private JavaScriptEditor javaScriptEditor;
	private IDocumentProvider documentProvider;
	private ScriptOrFnNode node;
	private JavaScriptLabelProvider javaScriptLabelProvider = new JavaScriptLabelProvider();

	public JavaScriptContentOutlinePage(IDocumentProvider documentProvider,JavaScriptEditor javaScriptEditor) {
		this.documentProvider = documentProvider;
		this.javaScriptEditor = javaScriptEditor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		TreeViewer viewer = getTreeViewer();
		viewer.setLabelProvider(javaScriptLabelProvider);
		viewer.setContentProvider(new JavaScriptContentProvider());
		viewer.addSelectionChangedListener(this);
	}

	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
		ISelection selection = event.getSelection();
		Node n = (Node)((IStructuredSelection)selection).getFirstElement();
		IDocument document = javaScriptEditor.getDocumentProvider().getDocument(javaScriptEditor.getEditorInput());
		String text = javaScriptLabelProvider.getText(n);
		int start = n.getLineno();
		int length= 5;
		int off = 0;
		if(n.getType() == Token.FUNCTION){
			start = ((FunctionNode)n).getEncodedSourceStart();
			start = document.get().indexOf("function",start);
			start += "function".length();
		}
		else{
			try {
				start = document.getLineOffset(start);
				start = document.get().indexOf("var",start);
				start += "var".length();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		off = document.get().indexOf(text,start); 
		try{
			javaScriptEditor.SetSeelected(off,text.length(),true);
		} catch (Exception x) {
			javaScriptEditor.resetHighlightRange();
		}		
	}

	public void setInput(final ScriptOrFnNode node) {
		this.node = node;
		try{
			Display.getDefault().asyncExec(new Runnable(){
				public void run() {
					getTreeViewer().setInput(node);
				}
			});
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}

package jsedit.editors;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.mozilla.javascript.ScriptOrFnNode;

public class JavaScriptContentOutlinePage extends ContentOutlinePage {

	private JavaScriptEditor javaScriptEditor;
	private IDocumentProvider documentProvider;

	public JavaScriptContentOutlinePage(IDocumentProvider documentProvider,JavaScriptEditor javaScriptEditor) {
		this.documentProvider = documentProvider;
		this.javaScriptEditor = javaScriptEditor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		TreeViewer viewer = getTreeViewer();
		viewer.setLabelProvider(new JavaScriptLabelProvider());
		viewer.setContentProvider(new JavaScriptContentProvider());
		viewer.addSelectionChangedListener(this);
	}

	public void setInput(final ScriptOrFnNode node) {
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

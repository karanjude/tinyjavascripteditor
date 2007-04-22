package jsedit.editors;

import jsedit.JavaScriptEditorPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Parser;

public class JavaScriptDocumentProvider extends FileDocumentProvider {

	public JavaScriptDocumentProvider() {
		super();
	}

	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		System.out.println("creating java script document");

		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner documentPartitioner = JavaScriptEditorPlugin
					.getDefault().getJavaScriptDocumentPartitioner();
			documentPartitioner.connect(document);
			document.setDocumentPartitioner(documentPartitioner);
			JavaScriptEditorPlugin.getDefault().setDocument(document);
		}

		return document;
	}

	@Override
	protected void doSaveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		System.out.println("saving java script file");

		super.doSaveDocument(monitor, element, document, overwrite);
	}

}

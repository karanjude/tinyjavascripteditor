package jsedit.editors;

import java.awt.Label;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.synth.Region;

import jsedit.JavaScriptEditorPlugin;
import jsedit.actions.DummyAction;
import jsedit.actions.RunAction;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.javaeditor.JavaSourceViewer;
import org.eclipse.jdt.ui.actions.IJavaEditorActionDefinitionIds;
import org.eclipse.jdt.ui.text.IJavaPartitions;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ScriptOrFnNode;

/**
 * 
 * @author karasing
 * 
 */
public class JavaScriptEditor extends TextEditor implements ErrorReporter,
		INavigationLocationProvider {

	private ColorManager colorManager;
	private IDocument document;
	String CONTENT_ASSIST_PROPOSALS = "jsedit.contentAssist.proposals";
	private JavaScriptContentOutlinePage outlinePage;
	private ScriptOrFnNode model;
	private JavaScriptDocumentProvider javaScriptDocumentProvider;

	public JavaScriptEditor() {
		super();
		colorManager = new ColorManager();
		setDocumentProvider(javaScriptDocumentProvider);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#getDocumentProvider()
	 */
	@Override
	public IDocumentProvider getDocumentProvider() {
		javaScriptDocumentProvider = new JavaScriptDocumentProvider(this);
		return super.getDocumentProvider();
	}

	@Override
	protected void createActions() {
		super.createActions();
		IAction contentAssistAction = new TextOperationAction(
				JavaScriptEditorMessages.getResourceBundle(),
				"ContentAssistProposal.", this,
				ISourceViewer.CONTENTASSIST_PROPOSALS);
		contentAssistAction
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction("ContentAssistProposal", contentAssistAction);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.editors.text.TextEditor#editorContextMenuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		menu.add(new RunAction(this.script()));
	}

	private void fillContextMenu() {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setSourceViewerConfiguration(new JavaScriptSourceViewerCoinfiguration(
				new ColorManager(), this));

		// /eJavaScriptSourceViewerCoinfiguration
		// javaScriptSourceViewerCoinfiguration = new
		// JavaScriptSourceViewerCoinfiguration();
		// setSourceViewerConfiguration(javaScriptSourceViewerCoinfiguration);
		// getSourceViewer().configure(javaScriptSourceViewerCoinfiguration);
		// System.out.println(JavaScriptEditorPlugin.getDefault().getDocument().get());
		System.out.println("initializeinf editor");
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		// final IEditorInput myinput = input;
		// IAnnotationModel annotationModel =
		// getDocumentProvider().getAnnotationModel(input);
		// annotationModel.addAnnotationModelListener(new
		// IAnnotationModelListener(){
		//
		// public void modelChanged(IAnnotationModel model) {
		// final IResource resource = (IResource)
		// myinput.getAdapter(IResource.class);
		// int loff = 0;
		// int dd = 0;
		// try {
		//					
		// loff =
		// JavaScriptEditorPlugin.getDefault().getDocument().getLineOffset(2) ;
		// dd =
		// JavaScriptEditorPlugin.getDefault().getDocument().getLineOffset(2);
		// } catch (BadLocationException e1) {
		// //
		// e1.printStackTrace();
		// }
		// final Map<String, Object> map = new HashMap<String, Object>();
		// map.put(IMarker.MESSAGE, "problem");
		// map.put(IMarker.SEVERITY, new Integer(IMarker.SEVERITY_ERROR));
		// map.put(IMarker.LINE_NUMBER, 2);
		// map.put(IMarker.CHAR_START,dd + 3);
		// map.put(IMarker.CHAR_END, dd + 5);
		// map.put(IMarker.TRANSIENT, Boolean.valueOf(true));
		//		            
		// IWorkspaceRunnable r= new IWorkspaceRunnable() {
		// public void run(IProgressMonitor monitor) throws CoreException {
		// IMarker marker= resource.createMarker(IMarker.PROBLEM);
		// marker.setAttributes(map);
		// }
		// };
		//
		// try {
		// if(!resource.getWorkspace().isTreeLocked() )
		// resource.getWorkspace().run(r, null,IWorkspace.AVOID_UPDATE, null);
		// } catch (CoreException e) {
		// e.printStackTrace();
		// }
		//
		// }
		//			
		// });
		// System.out.println("document created" +
		// "");
		// IDocument document2 =
		// JavaScriptEditorPlugin.getDefault().getDocument();
		// document2.addDocumentListener(new IDocumentListener(){
		//
		// public void documentAboutToBeChanged(DocumentEvent event) {
		// }
		//
		// public void documentChanged(DocumentEvent event) {
		// }
		// });
	}

	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {
		ISourceViewer sourceViewer = super.createSourceViewer(parent, ruler,
				styles);
		return sourceViewer;
	}

	@Override
	public boolean isDirty() {
		// JavaScriptScanner partitionScanner =
		// JavaScriptEditorPlugin.getDefault().getPartitionScanner();
		// IDocumentPartitioner javaScriptDocumentPartitioner =
		// JavaScriptEditorPlugin.getDefault().getJavaScriptDocumentPartitioner();
		// int tokenOffset = partitionScanner.getTokenOffset();
		// ITypedRegion partition =
		// javaScriptDocumentPartitioner.getPartition(tokenOffset);
		// ITypedRegion partition2 =
		// javaScriptDocumentPartitioner.getPartition(partitionScanner.getTokenOffset());
		// //System.out.println("[" + partition2.getOffset() + " - " +
		// partition2.getLength() + "]");
		// IDocument document =
		// JavaScriptEditorPlugin.getDefault().getDocument();
		// try {
		// System.out.println(
		// document.get(tokenOffset, partitionScanner.getTokenLength()));
		// } catch (BadLocationException e) {
		// e.printStackTrace();
		// }
		//		
		return super.isDirty();
	}

	public void error(String message, String sourceName, int line,
			String lineSource, int lineOffset) {
		final IResource resource = (IResource) getEditorInput().getAdapter(
				IResource.class);
		int lineContainingError = 0;
		int dd = 0;
		int len = 0;
		try {

			IRegion lineInformation = document.getLineInformation(line);
			lineContainingError = document.getLineOfOffset(lineInformation.getOffset());
			dd = document.getLineOffset(lineContainingError);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put(IMarker.MESSAGE, message);
		map.put(IMarker.SEVERITY, new Integer(IMarker.SEVERITY_ERROR));
		map.put(IMarker.LINE_NUMBER, line + 1);
		map.put(IMarker.CHAR_START, dd + lineOffset);
		map.put(IMarker.CHAR_END, dd + 1);
		IMarker marker;
		try {
			marker = resource
			.createMarker(JavaScriptEditorPlugin.PLUGIN_ID
					+ ".error");
			marker.setAttributes(map);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public EvaluatorException runtimeError(String message, String sourceName,
			int line, String lineSource, int lineOffset) {
		return new EvaluatorException(message, sourceName, line, lineSource,
				lineOffset);
	}

	public void warning(String message, String sourceName, int line,
			String lineSource, int lineOffset) {
		System.out.println("warning");
	}

	public void clearProblems() {
		final IResource resource = (IResource) getEditorInput().getAdapter(
				IResource.class);
		try {
			resource.deleteMarkers(IMarker.PROBLEM, true,
					IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object getAdapter(Class clazz) {
		if (IContentOutlinePage.class.equals(clazz)) {
			if (outlinePage == null) {
				outlinePage = new JavaScriptContentOutlinePage(
						getDocumentProvider(), this);
				return outlinePage;
			}
		}
		return super.getAdapter(clazz);
	}

	public ScriptOrFnNode getModel() {
		return this.model;
	}

	public void setModel(ScriptOrFnNode node) {
		this.model = model;
		outlinePage.setInput(node);
	}

	public void SetSeelected(int i, int j, boolean b) {
		ISourceViewer sourceViewer = getSourceViewer();
		sourceViewer.setSelectedRange(i, j);
		sourceViewer.revealRange(i, j);
	}

	public void format() {
		IContentFormatter contentFormatter = getSourceViewerConfiguration()
				.getContentFormatter(getSourceViewer());
		contentFormatter
				.format(this.document, new org.eclipse.jface.text.Region(0,
						this.document.getLength()));
	}

	/**
	 * @param document2
	 */
	public void setDocument(IDocument document) {
		this.document = document;
	}

	/**
	 * @return
	 */
	public String script() {
		return this.document.get();
	}

}

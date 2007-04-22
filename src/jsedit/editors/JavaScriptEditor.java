package jsedit.editors;

import java.util.HashMap;
import java.util.Map;

import jsedit.JavaScriptEditorPlugin;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Parser;

public class JavaScriptEditor extends TextEditor implements ErrorReporter {

	private ColorManager colorManager;
	private IDocument document;
	String CONTENT_ASSIST_PROPOSALS= "jsedit.contentAssist.proposals";
	
	public JavaScriptEditor() {
		super();
		colorManager = new ColorManager();
		JavaScriptDocumentProvider javaScriptDocumentProvider = new JavaScriptDocumentProvider();
		setDocumentProvider(javaScriptDocumentProvider);

	}
	
	@Override
	protected void createActions() {
		super.createActions();
		IAction contentAssistAction = new TextOperationAction(JavaScriptEditorMessages.getResourceBundle(),"ContentAssistProposal.",this,ISourceViewer.CONTENTASSIST_PROPOSALS);
		contentAssistAction.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction("ContentAssistProposal", contentAssistAction);
	
	}

	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
	
	
	
	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setSourceViewerConfiguration(new JavaScriptSourceViewerCoinfiguration(new ColorManager(),this));
		
	
		///eJavaScriptSourceViewerCoinfiguration javaScriptSourceViewerCoinfiguration = new JavaScriptSourceViewerCoinfiguration();
		//setSourceViewerConfiguration(javaScriptSourceViewerCoinfiguration);
	//	getSourceViewer().configure(javaScriptSourceViewerCoinfiguration);
		//System.out.println(JavaScriptEditorPlugin.getDefault().getDocument().get());
		System.out.println("initializeinf editor");	
	}
	
	
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
//		final IEditorInput myinput = input;
//		IAnnotationModel annotationModel = getDocumentProvider().getAnnotationModel(input);
//		annotationModel.addAnnotationModelListener(new IAnnotationModelListener(){
//
//			public void modelChanged(IAnnotationModel model) {
//				final IResource resource = (IResource) myinput.getAdapter(IResource.class);
//				int loff = 0;
//				int dd = 0;
//				try {
//					
//					 loff = JavaScriptEditorPlugin.getDefault().getDocument().getLineOffset(2) ;
//					  dd = JavaScriptEditorPlugin.getDefault().getDocument().getLineOffset(2);
//				} catch (BadLocationException e1) {
//					// 
//					e1.printStackTrace();
//				}
//				 final Map<String, Object> map = new HashMap<String, Object>();
//		            map.put(IMarker.MESSAGE, "problem");
//		            map.put(IMarker.SEVERITY, new Integer(IMarker.SEVERITY_ERROR));
//		            map.put(IMarker.LINE_NUMBER, 2);
//		            map.put(IMarker.CHAR_START,dd + 3);  
//		            map.put(IMarker.CHAR_END, dd + 5);
//		            map.put(IMarker.TRANSIENT, Boolean.valueOf(true));
//		            
//		        	IWorkspaceRunnable r= new IWorkspaceRunnable() {
//		    			public void run(IProgressMonitor monitor) throws CoreException {
//		    				IMarker marker= resource.createMarker(IMarker.PROBLEM);
//		    				marker.setAttributes(map);
//		    			}
//		    		};
//
//		    		try {
//		    			if(!resource.getWorkspace().isTreeLocked() )
//		    				resource.getWorkspace().run(r, null,IWorkspace.AVOID_UPDATE, null);
//					} catch (CoreException e) {
//						e.printStackTrace();
//					}
//
//			}
//			
//		});
		//		System.out.println("document created" +
//				"");
//		IDocument document2 = JavaScriptEditorPlugin.getDefault().getDocument();
//		document2.addDocumentListener(new IDocumentListener(){
//
//			public void documentAboutToBeChanged(DocumentEvent event) {
//			}
//
//			public void documentChanged(DocumentEvent event) {
//			}
//		});
	}

	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {
		ISourceViewer sourceViewer = super.createSourceViewer(parent, ruler, styles);
		return sourceViewer;
	}

	@Override
	public boolean isDirty() {
//		JavaScriptScanner partitionScanner = JavaScriptEditorPlugin.getDefault().getPartitionScanner();
//		IDocumentPartitioner javaScriptDocumentPartitioner = JavaScriptEditorPlugin.getDefault().getJavaScriptDocumentPartitioner();
//		int tokenOffset = partitionScanner.getTokenOffset();
//		ITypedRegion partition = javaScriptDocumentPartitioner.getPartition(tokenOffset);
//		ITypedRegion partition2 = javaScriptDocumentPartitioner.getPartition(partitionScanner.getTokenOffset());
//		//System.out.println("[" + partition2.getOffset() + " - " + partition2.getLength() + "]");
//				IDocument document = JavaScriptEditorPlugin.getDefault().getDocument();
//		try {
//			System.out.println(
//			document.get(tokenOffset, partitionScanner.getTokenLength()));
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//		
		return super.isDirty();
	}

	public void error(String message, String sourceName, int line,
			String lineSource, int lineOffset) {
		System.out.println(line + " " + lineSource + " " + lineOffset);
		final IResource resource = (IResource) getEditorInput().getAdapter(IResource.class);
		int loff = 0;
		int dd = 0;
		int len = 0;
		try {
			
			 IRegion lineInformation = JavaScriptEditorPlugin.getDefault().getDocument().getLineInformation(line);
			 loff = JavaScriptEditorPlugin.getDefault().getDocument().getLineOfOffset(lineInformation.getOffset()) ;
			 dd = JavaScriptEditorPlugin.getDefault().getDocument().getLineOffset(loff);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		 final Map<String, Object> map = new HashMap<String, Object>();
            map.put(IMarker.MESSAGE, message);
            map.put(IMarker.SEVERITY, new Integer(IMarker.SEVERITY_ERROR));
            map.put(IMarker.LINE_NUMBER, line + 1);
            map.put(IMarker.CHAR_START,dd + lineOffset);  
            map.put(IMarker.CHAR_END, dd + 1);
            map.put(IMarker.TRANSIENT, Boolean.valueOf(true));
            
        	IWorkspaceRunnable r= new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					IMarker marker= resource.createMarker(IMarker.PROBLEM);
    				marker.setAttributes(map);
    			}
    		};

    		try {
    			if(!resource.getWorkspace().isTreeLocked() )
    				resource.getWorkspace().run(r, null,IWorkspace.AVOID_UPDATE, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
	}

	public EvaluatorException runtimeError(String message, String sourceName,
			int line, String lineSource, int lineOffset) {
		return new EvaluatorException(message,sourceName,line,lineSource,lineOffset);
	}

	public void warning(String message, String sourceName, int line,
			String lineSource, int lineOffset) {
		System.out.println("warning");
	}

	public void clearProblems() {
		final IResource resource = (IResource) getEditorInput().getAdapter(IResource.class);
		try {
			resource.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

}

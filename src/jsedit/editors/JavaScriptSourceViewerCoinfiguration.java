package jsedit.editors;

import jsedit.JavaScriptEditorPlugin;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.internal.ui.propertiesfileeditor.PropertyValueScanner.AssignmentDetector;
import org.eclipse.jdt.internal.ui.text.JavaReconciler;
import org.eclipse.jdt.internal.ui.text.comment.CommentFormattingStrategy;
import org.eclipse.jdt.internal.ui.text.java.JavaFormattingStrategy;
import org.eclipse.jdt.ui.text.IJavaPartitions;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.texteditor.ContentAssistAction;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class JavaScriptSourceViewerCoinfiguration extends
		TextSourceViewerConfiguration {

	private ColorManager colorManager;
	private JavaScriptEditor javaScriptEditor;

	public JavaScriptSourceViewerCoinfiguration(ColorManager colorManager, JavaScriptEditor javaScriptEditor) {
		this.colorManager = colorManager;
		this.javaScriptEditor = javaScriptEditor;
	}

	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new JavaScriptAnnotationHover();
	}

	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType, int stateMask) {
		return new JavaScriptTextHover();
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		DefaultDamagerRepairer damageRepairer = new DefaultDamagerRepairer(
				getTagScanner());
		reconciler.setDamager(damageRepairer, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(damageRepairer, IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		if (javaScriptEditor != null && javaScriptEditor.isEditable()) {

			JavaScriptReconcilingStrategy javaScriptReconcilingStrategy = new JavaScriptReconcilingStrategy(javaScriptEditor);
			javaScriptReconcilingStrategy.setDocument(JavaScriptEditorPlugin
					.getDefault().getDocument());
			MonoReconciler reconciler = new MonoReconciler(
					javaScriptReconcilingStrategy, true);
			reconciler.setIsIncrementalReconciler(false);
			reconciler.setIsAllowedToModifyDocument(false);
			reconciler.setProgressMonitor(new NullProgressMonitor());
			reconciler.setDelay(200);

			return reconciler;
		}
		return null;

	}
	
	protected JavaScriptRuleScanner getTagScanner() {
		return new JavaScriptRuleScanner();
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assitant = new ContentAssistant();
		assitant.enableAutoActivation(true);
		assitant.setAutoActivationDelay(100);
		assitant.setContentAssistProcessor(new JavaScriptCompletionProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
		return assitant;
	}

}

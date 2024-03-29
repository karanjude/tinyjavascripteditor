package jsedit.editors;

import jsedit.JavaScriptEditorPlugin;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
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
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

public class JavaScriptSourceViewerCoinfiguration extends
		TextSourceViewerConfiguration {

	private ColorManager colorManager;
	private JavaScriptEditor javaScriptEditor;

	public JavaScriptSourceViewerCoinfiguration(ColorManager colorManager,
			JavaScriptEditor javaScriptEditor) {
		this.colorManager = colorManager;
		this.javaScriptEditor = javaScriptEditor;
	}

	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new JavaScriptAnnotationHover();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getContentFormatter(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		MultiPassContentFormatter formatter = new MultiPassContentFormatter(
				getConfiguredDocumentPartitioning(sourceViewer),
				IDocument.DEFAULT_CONTENT_TYPE);
		formatter.setMasterStrategy(new JavaScriptFormattingStrategy());
		return formatter;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		IAutoEditStrategy strategy = (IDocument.DEFAULT_CONTENT_TYPE
				.equals(contentType) ? new JavaScriptAutoIndentStrategy()
				: new DefaultIndentLineAutoEditStrategy());
		return new IAutoEditStrategy[] { strategy };
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

			JavaScriptReconcilingStrategy javaScriptReconcilingStrategy = new JavaScriptReconcilingStrategy(
					javaScriptEditor);
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
		assitant.setContentAssistProcessor(new JavaScriptCompletionProcessor(),
				IDocument.DEFAULT_CONTENT_TYPE);
		return assitant;
	}

}

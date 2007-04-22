package jsedit.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;

public class JavaScriptAnnotationHover implements IAnnotationHover {

	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
		IDocument document = sourceViewer.getDocument();
		try {
			IRegion region = document.getLineInformation(lineNumber);
			return document.get(region.getOffset() , region.getLength());
		} catch (BadLocationException e) {
		}
		
		return null;
	}

}
